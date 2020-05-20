package Utilidades;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class UFormat {
	public String decimalFormat(String pattern, double value ) {
		DecimalFormat xdf = new DecimalFormat(pattern,new DecimalFormatSymbols(Locale.US));
		return xdf.format(value);
	}
	
	public String dateFormat(Date d){
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Calendar cal = new GregorianCalendar();
		cal.setTime(d);
		return sdf.format(cal.getTime());
	}
}
