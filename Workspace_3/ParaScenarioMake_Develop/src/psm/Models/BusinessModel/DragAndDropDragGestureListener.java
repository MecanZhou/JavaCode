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
             // 在给定要显示的初始 Cursor、Transferable 对象和要使用的 DragSourceListener 的情况下开始拖动。
             dge.startDrag( DragSource.DefaultCopyDrop, dragAndDropTransferable, new DragAndDropDragSourceListener() );
         }
     }
 }
