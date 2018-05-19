 package psm.Models.BusinessModel;
 
 import java.awt.dnd.DragGestureEvent;
 import java.awt.dnd.DragGestureListener;
 import java.awt.dnd.DragSource;
 
 import javax.swing.JTree;
 import javax.swing.tree.DefaultMutableTreeNode;
 import javax.swing.tree.TreePath;
 
  /* Drag Gesture Listener */
  public class DragAndDropDragGestureListener implements DragGestureListener {
      public void dragGestureRecognized( DragGestureEvent dge ) {
         JTree tree = (JTree)dge.getComponent();
         TreePath path = tree.getSelectionPath();
          if( path != null ) {
             DefaultMutableTreeNode selection = ( DefaultMutableTreeNode )path.getLastPathComponent();
             DragAndDropTransferable dragAndDropTransferable = new DragAndDropTransferable( selection );
             // �ڸ���Ҫ��ʾ�ĳ�ʼ Cursor��Transferable �����Ҫʹ�õ� DragSourceListener ������¿�ʼ�϶���
             dge.startDrag( DragSource.DefaultCopyDrop, dragAndDropTransferable, new DragAndDropDragSourceListener() );
         }
     }
 }
