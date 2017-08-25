/*
 * Copyright 2017 ImpactDevelopment
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clientapi.command.executor;

import clientapi.command.Command;
import clientapi.command.exception.CommandException;
import clientapi.command.executor.sender.CommandSender;

/**
 * Directly executes commands that are passed to this executor
 *
 * @author Brady
 * @since 6/11/2017 9:27 AM
 */
public final class DirectExecutor implements CommandExecutor {

    @Override
    public void execute(Command command, CommandSender sender,
        String[] arguments) throws CommandException {
        command.execute(sender, arguments);
    }
}
