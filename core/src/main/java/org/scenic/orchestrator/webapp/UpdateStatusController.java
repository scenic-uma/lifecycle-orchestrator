package org.scenic.orchestrator.webapp;

import org.scenic.orchestrator.core.UpdaterManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UpdateStatusController {

    private final UpdaterManagement updaterManagement;

    @Autowired
    public UpdateStatusController(UpdaterManagement updaterManagement) {
        this.updaterManagement = updaterManagement;
    }

    @PutMapping("/update")
    public void update() throws InterruptedException {
        System.out.println("---Updating...");
        updaterManagement.updateApplication();
    }
}
