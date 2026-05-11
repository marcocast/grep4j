package org.grep4j.core.command.linux;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.grep4j.core.command.ExecutableCommand;
import org.grep4j.core.model.ServerDetails;

/**
 * Executes commands on the local machine via {@link ProcessBuilder}.
 * The error stream is merged into stdout so callers only read one stream.
 * A virtual thread drains the stream to prevent blocking.
 */
public class LocalCommandExecutor extends CommandExecutor {

    public LocalCommandExecutor(ServerDetails serverDetails) {
        super(serverDetails);
    }

    @Override
    public CommandExecutor execute(ExecutableCommand command) {
        try {
            executeCommand(command);
        } catch (Exception e) {
            throw new RuntimeException(
                    "ERROR: Unrecoverable error when performing local command " + e.getMessage(), e);
        }
        return this;
    }

    private void executeCommand(ExecutableCommand command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command.getCommandToExecute());
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // Drain stdout on a virtual thread so the process never blocks on a full pipe buffer.
        InputStream stdout = process.getInputStream();
        String[] output = new String[1];
        Thread drainThread = Thread.ofVirtual().start(() -> {
            try {
                output[0] = IOUtils.toString(stdout, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        process.waitFor();
        drainThread.join();
        result.append(output[0]);
        process.destroy();
    }
}
