package server.system.service;

import server.system.domain.ClimData;
import server.system.persistence.ClimDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/climDataService")
public class ClimDataRest {
    
    @Autowired
    public ClimDataRepo repo;
    
    @GetMapping("/{year}")
    public Iterable<ClimData> getByYear(@PathVariable String year, 
            @RequestHeader String tenantId) {
        return repo.getByYear(year);
    }
    
}
