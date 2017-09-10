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

package clientapi.value;

import clientapi.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * The implementation of IValue
 *
 * @see IValue
 *
 * @author Brady
 * @since 1/23/2017 12:00 PM
 */
public class Value<T> implements IValue<T> {

    /**
     * List of child values
     */
    private final List<IValue> children = new ArrayList<>();

    /**
     * Cache of values by ID, faster than creating streams every time a value is retrieved
     */
    private final Map<String, IValue> valueCache = new HashMap<>();

    /**
     * Name of the Value
     */
    private final String name;

    /**
     * ID of the parent value, null if there isn't a parent
     */
    private final String parent;

    /**
     * Description of the Value
     */
    private final String id;

    /**
     * Description of the Value
     */
    private final String description;

    /**
     * The Object that the field representing the Value is inside
     */
    private final Object object;

    /**
     * The Field representing the Value
     */
    private final Field field;

    public Value(String name, String parent, String id, String description, Object object, Field field) {
        this.name = name;
        this.parent = parent.length() > 0 ? parent : null;
        this.id = id;
        this.description = description;
        this.object = object;
        this.field = field;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getValue() {
        return (T) ReflectionUtils.getField(object, field);
    }

    @Override
    public void setValue(T value) {
        ReflectionUtils.setField(object, field, value);
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final String getDescription() {
        return this.description;
    }

    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public final boolean addValue(IValue value) {
        return getValue(value.getId()) == null && this.children.add(value);
    }

    @Override
    public final IValue getValue(String id) {
        return valueCache.computeIfAbsent(id, _id -> children.stream().filter(value -> value.getId().equals(_id)).findFirst().orElse(null));
    }

    @Override
    public final Collection<IValue> getValues() {
        return new ArrayList<>(this.children);
    }
}