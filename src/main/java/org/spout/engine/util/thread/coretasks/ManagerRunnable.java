/*
 * This file is part of Spout.
 *
 * Copyright (c) 2011 Spout LLC <http://www.spout.org/>
 * Spout is licensed under the Spout License Version 1.
 *
 * Spout is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Spout is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.engine.util.thread.coretasks;

import java.util.Objects;
import java.util.concurrent.Callable;
import org.spout.engine.util.thread.AsyncManager;

public abstract class ManagerRunnable implements Callable<Void> {
	private final AsyncManager manager;

	public ManagerRunnable(AsyncManager manager) {
		this.manager = manager;
	}

	@Override
	public final Void call() {
		manager.setExecutionThread(Thread.currentThread());
		runTask();
        return null;
	}

	protected abstract void runTask();

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.manager);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ManagerRunnable other = (ManagerRunnable) obj;
        if (!Objects.equals(this.manager, other.manager))
            return false;
        return true;
    }
}
