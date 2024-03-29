package ua.destro967.mailPigeon.utils;

import ua.destro967.mailPigeon.dto.userList.UserListDto;

import java.util.Comparator;
import java.util.Date;

public class UserListDtoComparator implements Comparator<UserListDto> {
    @Override
    public int compare(UserListDto o1, UserListDto o2) {
        if (o1.getMessage().getTime().equals("") ||o2.getMessage().getTime().equals("") )
            return 0;

        Date date1 = new Date("07/06/2013 " + o1.getMessage().getTime());
        Date date2 = new Date("07/06/2013 " + o2.getMessage().getTime());
        return date1.compareTo(date2);
    }
}

