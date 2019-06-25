package by.butramyou.todolist.exeption;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidDeadlineExeption extends Exception {

    public Boolean inValidDeadline(String dayDeadline) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date now = new Date();
        Date deadline = dateFormat.parse(dayDeadline);
        if (deadline.after(now)){
            return true;
        }
        return false;
    }
}
