package business.abstracts;

import java.util.List;
import business.dto.requests.CreateSharedDataRequest;
import business.dto.requests.UpdateSharedDataRequest; 
import business.dto.responses.SharedDataResponse;

public interface SharedDataService {
    void add(CreateSharedDataRequest request);
    
    List<SharedDataResponse> getAll();
    
    void update(UpdateSharedDataRequest request);
}