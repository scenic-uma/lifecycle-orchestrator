package org.scenic.orchestrator.webapp;

import java.util.concurrent.ExecutorService;

import org.scenic.orchestrator.core.UpdaterManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UpdateStatusController {

    private final UpdaterManagement updaterManagement;

    private final ExecutorService executorService;

    @Autowired
    public UpdateStatusController(UpdaterManagement updaterManagement, ExecutorService executorService) {
        this.updaterManagement = updaterManagement;
        this.executorService = executorService;
    }

    @PutMapping("/update")
    public void update() throws InterruptedException {
        System.out.println("---Updating...");
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("executing in background");
                try{
                    updaterManagement.updateApplication();
                } catch(Exception e){
                    System.out.println("Error in update " + e.getMessage() + e.getCause());
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("Executing");
        //updaterManagement.updateApplication();
    }
}
