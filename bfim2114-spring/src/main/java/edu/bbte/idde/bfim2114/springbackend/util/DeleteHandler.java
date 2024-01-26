package edu.bbte.idde.bfim2114.springbackend.util;

import edu.bbte.idde.bfim2114.springbackend.exception.DeleteMissingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class DeleteHandler {

    @Value("${fail.on.delete.missing}")
    private boolean failOnDeleteMissing;

    @Value("${is.delete.missing.fatal}")
    private boolean isDeleteMissingFatal;

    public ResponseEntity<?> handleDeleteMissing(boolean isMissing) {
        if (isMissing) {
            if (failOnDeleteMissing) {
                if (isDeleteMissingFatal) {
                    return ResponseEntity.internalServerError().body(
                        new DeleteMissingException("Deleting missing entity is fatal."));
                } else {
                    return ResponseEntity.notFound().build();
                }
            } else {
                return ResponseEntity.noContent()
                    .build();
            }
        }
        return ResponseEntity.noContent().build();
    }
}
