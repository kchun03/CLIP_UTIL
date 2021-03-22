import com.clipsoft.scheduler.ClipSchedulerAgent;
import com.clipsoft.scheduler.ParamFields;
import com.clipsoft.scheduler.ResultCode;
import com.clipsoft.scheduler.options.DataType;
import com.clipsoft.scheduler.options.ExportType;
import com.clipsoft.scheduler.options.SchedulerType;

public class exportTest {

	public static void main(String[] args) {
		ClipSchedulerAgent sa = new ClipSchedulerAgent("http://localhost:28080/ClipReport5-59/ClipSchedulerAgency.jsp");
	    String crfName = "oracle1.crf";
	    String data = "{\"data\":1}";
	    ParamFields paramFields = null;
	    String exportFileName = "ClipPdf";
	    ResultCode resultCode = sa.addScheduler(crfName, data, DataType.JSON, paramFields, exportFileName, ExportType.PDF, SchedulerType.NOWSYNC);
	    //ResultCode resultCode = sa.addScheduler(crfName, "oracle1_dojin", DataType.DB, paramFields, exportFileName, ExportType.PDF, SchedulerType.NOW); 
	    System.out.println(resultCode.getResultCode());
	}

}
