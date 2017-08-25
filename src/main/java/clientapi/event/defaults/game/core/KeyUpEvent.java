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

package clientapi.event.defaults.game.core;

/**
 * Event called when a Key is released outside of a GUI while in-game
 *
 * @see ClickEvent
 * @author Leaf
 * @since 2.1
 */
public final class KeyUpEvent extends KeyEvent {

    public KeyUpEvent(int key, char character) {
        super(key, character);
    }
}
