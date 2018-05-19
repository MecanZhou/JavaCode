import hla.rti.*;
import se.pitch.prti.FederateAmbassador_Impl;
import se.pitch.prti.RTI;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.objectspace.jgl.HashMap;

class Chat extends FederateAmbassador_Impl
{
   private RTIambassador _rtiAmbassador;
   private int _messageId1;
   private int _parameterIdText1;
   private int _parameterSender1;  
   private int _messageId2;
   private int _parameterIdText2;
   private int _parameterSender2;
   private int _participantClass;
   private int _attributeDummy;
   private HashMap _participants = new HashMap();

   public static void main(String[] args)
   {
      new Chat().run(args);
   }
   
   public void run(String[] args)
   {
      try {
         String rtiHost = (args.length > 0) ? args[0] : "localhost";
         _rtiAmbassador = RTI.getRTIambassador(rtiHost, 8989);//��ʼ��RTI��ʹ
         try {
            _rtiAmbassador.createFederationExecution(//��������
               "ChatRoom", new File("Chat.fed").toURL());
         } catch (FederationExecutionAlreadyExists ignored) {
         }
         _rtiAmbassador.joinFederationExecution("Chat", "ChatRoom", this);//��������
         
         _messageId1 = _rtiAmbassador.getInteractionClassHandle("Communication1");//������������
         _parameterIdText1 = _rtiAmbassador.getParameterHandle("Message1", _messageId1);//��ȡ����������ֵ
         _parameterSender1 = _rtiAmbassador.getParameterHandle("Sender1", _messageId1);//��ȡ����������ֵ
         _rtiAmbassador.subscribeInteractionClass(_messageId1);//����������
         _rtiAmbassador.publishInteractionClass(_messageId1);//���ͽ�����
       // 
         _messageId2 = _rtiAmbassador.getInteractionClassHandle("Communication2");//������������
         _parameterIdText2 = _rtiAmbassador.getParameterHandle("Message2", _messageId2);//��ȡ����������ֵ
         _parameterSender2 = _rtiAmbassador.getParameterHandle("Sender2", _messageId2);//��ȡ����������ֵ
         _rtiAmbassador.subscribeInteractionClass(_messageId2);//����������
         _rtiAmbassador.publishInteractionClass(_messageId2);//���ͽ�����
         
         _participantClass = _rtiAmbassador.getObjectClassHandle("Participant");//��ȡ��������
         _attributeDummy = _rtiAmbassador.getAttributeHandle("Dummy", _participantClass);//��ȡ����������ֵ

         AttributeHandleSet attributes = RTI.attributeHandleSetFactory().create();
         attributes.add(_attributeDummy);
         _rtiAmbassador.publishObjectClass(_participantClass, attributes);
         _rtiAmbassador.subscribeObjectClassAttributes(_participantClass, attributes);

         Thread.sleep(500);

         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

         System.out.print("Enter your name > ");
         String myName = in.readLine();
         final int myInstanceId = _rtiAmbassador.registerObjectInstance(_participantClass, myName);//ע�������ʵ����ÿ�������Ա��������һ��

         System.out.println("Type messages you want to send. To exit, type . <ENTER>");
         while (true) {
            System.out.print("> ");
            String cmdline = in.readLine();
            
            if (cmdline.equals(".")) {
               break;
            }
            SuppliedParameters parameters = RTI.suppliedParametersFactory().create(2);//�������Լ�����
            byte[] value = cmdline.getBytes();
            parameters.add(_parameterIdText2, value);
            parameters.add(_parameterSender2, encodeInt(myInstanceId));

            _rtiAmbassador.sendInteraction(_messageId2, parameters, null);
         }
         
         _rtiAmbassador.resignFederationExecution(
            ResignAction.DELETE_OBJECTS_AND_RELEASE_ATTRIBUTES);
         try {
            _rtiAmbassador.destroyFederationExecution("ChatRoom");
         } catch (FederatesCurrentlyJoined ignored) {
         }
      } catch (RTIinternalError e) {
         System.out.println("Limit exceeded");
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   byte[] encodeInt(int i)
   {
      return new byte[] {
         (byte)((i >>> 24) & 0xFF),
         (byte)((i >>> 16) & 0xFF),
         (byte)((i >>>  8) & 0xFF),
         (byte)((i >>>  0) & 0xFF)
      };
   }

   int decodeInt(byte[] buffer)
   {
      return
         ((buffer[0] & 0xff) << 24) +
         ((buffer[1] & 0xff) << 16) +
         ((buffer[2] & 0xff) <<  8) +
         ((buffer[3] & 0xff) <<  0);
   }

   public void receiveInteraction (
      int                 interactionClass, 
      ReceivedInteraction theInteraction,  
      byte[]              userSuppliedTag)         
   throws
      InteractionClassNotKnown,
      InteractionParameterNotKnown,
      FederateInternalError
   {
      try {
         if (interactionClass == _messageId2) {
            String message2 = "";
            String sender2 = "Unknown";
            for (int i = 0; i < theInteraction.size(); ++i) {
               if (theInteraction.getParameterHandle(i) == _parameterIdText2) {//�������涩��������ϵȡ����Ӧ������
                  message2 = new String(theInteraction.getValueReference(i));
               }
               if (theInteraction.getParameterHandle(i) == _parameterSender2) {
                  int senderId = decodeInt(theInteraction.getValueReference(i));
                  sender2 = (String)_participants.get(new Integer(senderId));
               }
            }
            System.out.println(sender2 + ": " + message2);
         }
      } catch (ArrayIndexOutOfBounds e) {
         System.out.println(e.getMessage());
      }
   }

   public void discoverObjectInstance(int instanceId, int classId, String instanceName)
      throws CouldNotDiscover, ObjectClassNotKnown, FederateInternalError
   {
      System.out.println(instanceName + " has joined.");
      _participants.add(new Integer(instanceId), instanceName);
   }

   public void removeObjectInstance(int instanceId, byte[] userSuppliedTag)
      throws ObjectNotKnown, FederateInternalError
   {
      String instanceName = (String)_participants.get(new Integer(instanceId));
      if (instanceName != null) {
         System.out.println(instanceName + " has left.");
         _participants.remove(new Integer(instanceId));
      }
   }
}
