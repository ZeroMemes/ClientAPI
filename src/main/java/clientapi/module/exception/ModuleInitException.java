/*
 * Copyright 2018 ImpactDevelopment
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

package clientapi.module.exception;

import clientapi.module.Module;

/**
 * Thrown during the initialization of a {@link Module} if
 * invalid or insufficient data is provided.
 *
 * @author Brady
 * @since 6/29/2017
 */
public final class ModuleInitException extends RuntimeException {

    public ModuleInitException(String message) {
        super(message);
    }
}
