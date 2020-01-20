package com.github.fluorumlabs;

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.http.FtBasic;
import org.takes.rs.RsWithBody;
import org.takes.tk.TkWithType;

import java.io.IOException;

/**
 * Goal which starts a HTTP server that will terminate maven process on request.
 *
 * @goal kill
 */
@Mojo(name = "kill",
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
        requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class ReaperMojo
        extends AbstractMojo {
    /**
     * Port to listen to. Any incoming HTTP request to this port will result in
     * JVM exit.
     */
    @Parameter(defaultValue = "8989")
    private int port;

    public void execute()
            throws MojoExecutionException {
        new Thread(this::runReaperServer).start();
    }

    private void runReaperServer() {
        try {
            new FtBasic(new TkFork(
                    new FkRegex(".*",
                            new TkWithType(req -> {
                                getLog().info("\n\nTERMINATING PROCESS DUE TO INCOMING HTTP REQUEST TO PORT " + port + "\n\n");

                                System.exit(0);
                                return new RsWithBody("OK");
                            }, "text/plain"))
            ), port).start(() -> false);
            getLog().info("INCOMING HTTP REQUEST TO PORT " + port + " WILL END THIS MAVEN PROCESS");
        } catch (IOException e) {
            getLog().error("Error starting reaper:kill server", e);
        }
    }

}
