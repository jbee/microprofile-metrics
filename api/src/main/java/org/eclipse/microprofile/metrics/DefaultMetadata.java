/*
 **********************************************************************
 * Copyright (c) 2018, 2019 Contributors to the Eclipse Foundation
 *               2018 Red Hat, Inc. and/or its affiliates
 *               and other contributors as indicated by the @author tags.
 *
 * See the NOTICES file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 **********************************************************************/
package org.eclipse.microprofile.metrics;

import java.util.Objects;
import java.util.Optional;

/**
 * The default implementation of {@link Metadata}
 */
public class DefaultMetadata implements Metadata {

    /**
     * Name of the metric.
     * <p>
     * A required field which holds the name of the metric object.
     * </p>
     */
    private final String name;

    /**
     * Display name of the metric. If not set, the name is taken.
     * <p>
     * An optional field which holds the display (Friendly) name of the metric object.
     * By default it is set to the name of the metric object.
     * </p>
     */
    private final String displayName;

    /**
     * A human readable description.
     * <p>
     * An optional field which holds the description of the metric object.
     * </p>
     */
    private final String description;

    /**
     * Type of the metric.
     * <p>
     * A required field which holds the type of the metric object.
     * </p>
     */
    private final MetricType type;
    /**
     * Unit of the metric.
     * <p>
     * An optional field which holds the Unit of the metric object.
     * </p>
     */
    private final String unit;

    protected DefaultMetadata(String name, String displayName, String description,
                              MetricType type, String unit) {
        this.name = name;
        this.displayName = displayName;
        this.description = description;
        this.type = type;
        this.unit = unit;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return Optional.ofNullable(displayName).orElse(name);
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public String getType() {
        return Optional.ofNullable(type).orElse(MetricType.INVALID).toString();
    }

    @Override
    public MetricType getTypeRaw() {
        return Optional.ofNullable(type).orElse(MetricType.INVALID);
    }

    @Override
    public Optional<String> getUnit() {
        return Optional.ofNullable(unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadata)) {
            return false;
        }
        Metadata that = (Metadata) o;

        //Retrieve the Optional value or set to the "defaults" if empty
        String thatDescription = (that.getDescription().isPresent()) ? that.getDescription().get() : null;
        String thatUnit = (that.getUnit().isPresent()) ? that.getUnit().get() : MetricUnits.NONE;

        //Need to use this.getDisplayname() and this.getTypeRaw() for the Optional.orElse() logic
        return Objects.equals(name, that.getName()) &&
                Objects.equals(this.getDisplayName(), that.getDisplayName()) &&
                Objects.equals(description, thatDescription) &&
                Objects.equals(unit, thatUnit) &&
                Objects.equals(this.getTypeRaw(), that.getTypeRaw());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, displayName, description, unit, type);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DefaultMetadata{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", unit='").append(unit).append('\'');
        if(description != null) {
            sb.append(", description='").append(description).append('\'');
        }
        else {
            sb.append(", description=null");
        }
        if(displayName != null) {
            sb.append(", displayName='").append(displayName).append('\'');
        }
        else {
            sb.append(", displayName=null");
        }
        sb.append('}');
        return sb.toString();
    }
}
