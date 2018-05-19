package psm.Models.BusinessModel;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceContext;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class DragAndDropDragSourceListener implements DragSourceListener {

	@Override
	public void dragDropEnd(DragSourceDropEvent e) {
        if( e.getDropSuccess() ) {
            int dropAction = e.getDropAction();
             if( dropAction == DnDConstants.ACTION_MOVE ) {
                //System.out.println( "MOVE: remove node" );              
            }
        }
	}

	@Override
	public void dragEnter(DragSourceDragEvent e) {
        DragSourceContext context = e.getDragSourceContext();
        int dropAction = e.getDropAction();
         if( ( dropAction & DnDConstants.ACTION_COPY ) != 0 ) {
            context.setCursor( DragSource.DefaultCopyDrop );
         } else if( ( dropAction & DnDConstants.ACTION_MOVE ) != 0 ) {
            context.setCursor( DragSource.DefaultMoveDrop );
         } else {
            context.setCursor( DragSource.DefaultCopyNoDrop );
        }


	}

	@Override
	public void dragExit(DragSourceEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DragSourceDragEvent arg0) {
		// TODO Auto-generated method stub

	}

}
