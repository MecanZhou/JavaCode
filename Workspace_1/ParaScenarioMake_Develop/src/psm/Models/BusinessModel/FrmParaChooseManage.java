package psm.Models.BusinessModel;

import java.awt.Point;

import psm.Models.DataModel.Formula;
import psm.Models.DataModel.Line;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.MemberObject;

public class FrmParaChooseManage {
	
	Formula newFormula = FormulaManage.getFormula();

	
	public Line DoGetSelectLine() {
		
		Line selectLine=LogicalStructureManage.temLine;
		return selectLine;
	}
}
