/**
 * 
 */
package edu.jhun.tool;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




/**
 * @author Administrator
 * @time 2018��3��1������2:33:09
 *
 */
public class ExportExcel {

	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;

	public ExportExcel(HSSFWorkbook workbook, HSSFSheet sheet) {
		super();
		this.wb = workbook;
		this.sheet = sheet;
	}

	public HSSFWorkbook getWorkbook() {
		return wb;
	}
	public void setWorkbook(HSSFWorkbook workbook) {
		this.wb = workbook;
	}
	public HSSFSheet getSheet() {
		return sheet;
	}
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public void createNormalHead(String headString, int colSum) throws Exception{
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		row.setHeight((short) 800);
		cell.setCellValue(new HSSFRichTextString(headString));
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, colSum));
		this.createCellPicture(wb, sheet.createDrawingPatriarch(), "logo.png", 0, 0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		//cellStyle.setWrapText(true);

		HSSFFont font = wb.createFont();
		font.setBold(true);
		font.setFontName("����");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	public void createColumHeader(String[] columHeader) {  

		// ������ͷ  
		HSSFRow row2 = sheet.createRow(2);  

		// ָ���и�  
		//row2.setHeight((short) 600);  

		HSSFCellStyle cellStyle = wb.createCellStyle();  
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // ָ����Ԫ����ж���  
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// ָ����Ԫ��ֱ���ж���  
		cellStyle.setWrapText(true);// ָ����Ԫ���Զ�����  

		// ��Ԫ������  
		HSSFFont font = wb.createFont();  
		font.setBold(true);  
		font.setFontName("����");  
		//font.setFontHeight((short) 250);  
		cellStyle.setFont(font);  


		/*  
		 * cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // ���õ�Ԫ��ı߿�Ϊ����  
		 * cellStyle.setBottomBorderColor(HSSFColor.BLACK.index); // ���õ�Ԫ��ı߿���ɫ��  
		 * cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		 * cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);  
		 * cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		 * cellStyle.setRightBorderColor(HSSFColor.BLACK.index);  
		 * cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  
		 * cellStyle.setTopBorderColor(HSSFColor.BLACK.index);  
		 */ 

		// ���õ�Ԫ�񱳾�ɫ  
		//cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
		//cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);;  

		HSSFCell cell = null;  

		for (int i = 0; i < columHeader.length; i++) {  
			cell = row2.createCell(i);  
			cell.setCellType(HSSFCell.ENCODING_UTF_16); 
			cell.setCellStyle(cellStyle);  
			cell.setCellValue(new HSSFRichTextString(columHeader[i]));  
		}  

	}  

	/**  
	 * �������ݵ�Ԫ��  
	 *   
	 * @param wb  
	 *            HSSFWorkbook  
	 * @param row  
	 *            HSSFRow  
	 * @param col  
	 *            short�͵�������  
	 * @param align  
	 *            ���뷽ʽ  
	 * @param val  
	 *            ��ֵ  
	 */ 
	public void cteateCell(HSSFWorkbook wb, HSSFRow row, int col, HorizontalAlignment align,  
			String val) {  
		HSSFCell cell = row.createCell(col);  
		cell.setCellType(HSSFCell.ENCODING_UTF_16);  
		cell.setCellValue(new HSSFRichTextString(val));  
		HSSFCellStyle cellstyle = wb.createCellStyle();  
		cellstyle.setAlignment(align);
		cell.setCellStyle(cellstyle);  
	}  

	/**
	 * ͼƬ����
	 */
	public void createCellPicture(HSSFWorkbook workbook, HSSFPatriarch patriarch, String url, int col, int row) throws Exception{
		//�ȰѶ�������ͼƬ�ŵ�һ��ByteArrayOutputStream�У��Ա����ByteArray
		BufferedImage bufferImg =null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		bufferImg = ImageIO.read(new File(url)); 
		ImageIO.write(bufferImg,"png",byteArrayOut);
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 100,1023, 255, (short) col, row, (short) col, row);
		anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);;
		//����ͼƬ
		patriarch.createPicture(anchor , workbook.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_PNG));
		byteArrayOut.flush();
		byteArrayOut.close();
	}

	public void insert(JSONArray jArray,ExportExcel excel, HSSFWorkbook workbook) {
		for (int i = 0; i < jArray.size(); i++) {
			JSONObject jsonObject = jArray.getJSONObject(i);
			HSSFRow row3 = sheet.createRow(i+3);
			for(int j=1;j<=jsonObject.size()+1;j++){
				if (j==1) {
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, String.valueOf((i+1)*j));
				}else if(j==2){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("date"));
				}else if(j==3){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("time"));
				}else if(j==4){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("downtime"));
				}else if(j==5){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("handletime"));
				}else if(j==6){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("downdesc"));
				}else if(j==7){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("method"));
				}else if(j==8){
					excel.cteateCell(workbook, row3, j-1, HorizontalAlignment.CENTER, jsonObject.getString("staff"));
				}
				row3.setHeight((short) (255.86*1+184.27));
			}
		}
	}
	public void printExcel(String value, HSSFWorkbook workbook) throws Exception {
		this.createNormalHead("�ӷ氲���أ��人���������޹�˾", 7);
		String[] columHeader = {"���","����","ʱ��","����ʱ��","���ϴ���ʱ��","��������","������","���ϴ�����"};
		this.createColumHeader(columHeader);
		JSONObject jsonObject  = JSONObject.fromObject(value);
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		this.insert(jsonArray, this, workbook);
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		File file = new File("ex.xls");
		try {
			fileOutputStream = new FileOutputStream(file);
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Print.printExcel("\\ex.xls", 1);
	}
	public static void main(String[] args) {
		String value = "{data:[{\"date\":\"2018/03/01\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"},"
				+ "{\"date\":\"2018/03/02\",\"time\":\"2\",\"downtime\":\"3\",\"handletime\":\"4\",\"downdesc\":\"5\",\"method\":\"6\",\"staff\":\"7\"}]}";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("new sheet");
		for (int i = 0; i < 8; i++) {
			sheet.setColumnWidth(i,(int) (255.86*10+184.27));
		}
		sheet.setGridsPrinted(true);
		ExportExcel excel = new ExportExcel(workbook, sheet);
		try {
			excel.printExcel(value, workbook);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
