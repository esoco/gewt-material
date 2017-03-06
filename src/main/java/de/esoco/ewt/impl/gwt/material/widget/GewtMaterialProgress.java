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


/********************************************************************
 * Material progress bar that implements the GEWT progress bar interface.
 *
 * @author eso
 */
public class GewtMaterialProgress extends MaterialProgress
	implements IsProgressBarWidget
{
	//~ Instance fields --------------------------------------------------------

	private int nMaximum  = 0;
	private int nMinimum  = 0;
	private int nProgress = 0;

	//~ Constructors -----------------------------------------------------------

	/***************************************
	 * Creates a new instance.
	 */
	public GewtMaterialProgress()
	{
		setType(ProgressType.INDETERMINATE);
	}

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getMaximum()
	{
		return nMaximum;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimum()
	{
		return nMinimum;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public int getProgress()
	{
		return nProgress;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setMaximum(int nMaximum)
	{
		this.nMaximum = nMaximum;
		update();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setMinimum(int nMinimum)
	{
		this.nMinimum = nMinimum;
		update();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setProgress(int nProgress)
	{
		this.nProgress = nProgress;
		setPercent((nProgress - nMinimum) * 100 / (nMaximum - nMinimum));
	}

	/***************************************
	 * Updates this instance according to it's current state.
	 */
	protected void update()
	{
		if (nMaximum != nMinimum)
		{
			setType(ProgressType.DETERMINATE);
			setProgress(nProgress);
		}
		else
		{
			setType(ProgressType.INDETERMINATE);
			setPercent(100);
		}
	}
}
