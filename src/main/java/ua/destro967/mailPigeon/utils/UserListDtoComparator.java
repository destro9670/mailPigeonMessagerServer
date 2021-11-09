package ua.destro967.mailPigeon.utils;

import ua.destro967.mailPigeon.dto.userList.UserListDto;

import java.util.Comparator;
import java.util.Date;

public class UserListDtoComparator implements Comparator<UserListDto> {
    @Override
    public int compare(UserListDto o1, UserListDto o2) {
        Date date1 = new Date(o1.getMessage().getTime());
        Date date2 = new Date(o2.getMessage().getTime());
        return date1.compareTo(date2);
    }
}

