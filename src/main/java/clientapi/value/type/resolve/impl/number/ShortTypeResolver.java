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

package clientapi.value.type.resolve.impl.number;

import clientapi.value.annotation.number.ShortValue;
import clientapi.value.type.number.ShortType;

import java.lang.reflect.Field;

/**
 * @author Brady
 * @since 9/1/2018
 */
public final class ShortTypeResolver implements NumberTypeResolver<ShortType, Short> {

    @Override
    public final ShortType resolve(Object parent, Field field) {
        ShortValue num = field.getAnnotation(ShortValue.class);
        return resolve0(ShortType::new, field.getType().isPrimitive(), parent, field, num.min(), num.max(), num.interval());
    }
}
