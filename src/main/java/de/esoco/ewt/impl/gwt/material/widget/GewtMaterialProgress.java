//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2017 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//	  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.ui.MaterialProgress;

import de.esoco.ewt.component.ProgressBar.IsProgressBarWidget;

/**
 * Material progress bar that implements the GEWT progress bar interface.
 *
 * @author eso
 */
public class GewtMaterialProgress extends MaterialProgress
	implements IsProgressBarWidget {

	private int maximum = 0;

	private int minimum = 0;

	private int progress = 0;

	/**
	 * Creates a new instance.
	 */
	public GewtMaterialProgress() {
		setType(ProgressType.INDETERMINATE);
	}

	@Override
	public int getMaximum() {
		return maximum;
	}

	@Override
	public int getMinimum() {
		return minimum;
	}

	@Override
	public int getProgress() {
		return progress;
	}

	@Override
	public void setMaximum(int maximum) {
		this.maximum = maximum;
		update();
	}

	@Override
	public void setMinimum(int minimum) {
		this.minimum = minimum;
		update();
	}

	@Override
	public void setProgress(int progress) {
		this.progress = progress;
		setPercent((progress - minimum) * 100 / (maximum - minimum));
	}

	/**
	 * Updates this instance according to it's current state.
	 */
	protected void update() {
		if (maximum != minimum) {
			setType(ProgressType.DETERMINATE);
			setProgress(progress);
		} else {
			setType(ProgressType.INDETERMINATE);
			setPercent(100);
		}
	}
}
