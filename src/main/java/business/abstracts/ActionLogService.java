package business.abstracts;

import entities.enums.ActionType;

public interface ActionLogService {
    void log(ActionType actionType, String detail, Long targetDataId);
}