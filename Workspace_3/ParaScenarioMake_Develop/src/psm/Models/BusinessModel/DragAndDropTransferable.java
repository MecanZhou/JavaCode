package psm.Models.BusinessModel;
 
 import java.awt.datatransfer.DataFlavor;
 import java.awt.datatransfer.Transferable;
 import java.awt.datatransfer.UnsupportedFlavorException;
 import java.io.IOException;
 
 import javax.swing.tree.DefaultMutableTreeNode;
 
  /* Drop Transferable */
  public class DragAndDropTransferable implements Transferable {
     private DefaultMutableTreeNode treeNode;
      public DragAndDropTransferable( DefaultMutableTreeNode treeNode ) {
         this.treeNode = treeNode;
     }
      DataFlavor[] flavors = { DataFlavor.stringFlavor };
      public DataFlavor[] getTransferDataFlavors() {
         return flavors;
     }
      public boolean isDataFlavorSupported( DataFlavor flavor ) {
          for( DataFlavor df : flavors ) {
              if( df.equals( flavor ) ) {
                 return true;
             }
         }
         return false;
     }
      public Object getTransferData( DataFlavor df ) throws UnsupportedFlavorException, IOException {
         return treeNode;
     }
 }
