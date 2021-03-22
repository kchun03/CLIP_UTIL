import com.clipsoft.scheduler.ClipSchedulerAgent;
import com.clipsoft.scheduler.ParamFields;
import com.clipsoft.scheduler.ResultCode;
import com.clipsoft.scheduler.options.DataType;
import com.clipsoft.scheduler.options.ExportType;
import com.clipsoft.scheduler.options.SchedulerType;

public class scheduler {
	public static void main(String[] args) {
		ClipSchedulerAgent sa = new ClipSchedulerAgent("http://localhost:28080/ClipReport5-59/ClipSchedulerAgency.jsp");
		String crfName = "oracle1.crf";
		String data = "{\"data\":\"oracle1_dojin\"}";
		ParamFields paramFields = null;
		String exportFileName = "ClipPdf";
		long start = System.currentTimeMillis();
		ResultCode resultCode = sa.addScheduler(crfName, data, DataType.JSON, paramFields, exportFileName, ExportType.PDF, SchedulerType.NOW);
		//ResultCode resultCode = sa.addScheduler(crfName, "oracle1_dojin", DataType.DB, paramFields, exportFileName, ExportType.PDF, SchedulerType.NOW);
		long end = System.currentTimeMillis();
		
		System.out.println("time : " + (end - start) + " ms");
		
		
		System.out.println("report1 code>> " +resultCode.getResultCode() + " message >>> " + resultCode.getResultMessage());
	}
}
