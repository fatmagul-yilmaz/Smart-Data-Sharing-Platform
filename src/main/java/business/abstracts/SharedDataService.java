package business.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;

import business.dto.requests.CreateSharedDataRequest;
import business.dto.requests.UpdateSharedDataRequest;
import business.dto.responses.SharedDataResponse;
public interface SharedDataService {
    void add(CreateSharedDataRequest request);
    
    List<SharedDataResponse> getAll(Pageable pageable);
    
    void update(UpdateSharedDataRequest request);

    
}