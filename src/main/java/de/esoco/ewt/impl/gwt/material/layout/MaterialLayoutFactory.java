//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2016 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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
package de.esoco.ewt.impl.gwt.material.layout;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.component.MainView.MainViewLayout;
import de.esoco.ewt.component.SplitPanel;
import de.esoco.ewt.component.StackPanel;
import de.esoco.ewt.layout.ContentLayout;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.layout.LayoutMapper;
import de.esoco.ewt.layout.MenuLayout;


/********************************************************************
 * A factory that maps GEWT layouts to new layouts that create GwtMaterialDesign
 * panels.
 *
 * @author eso
 */
public class MaterialLayoutFactory implements LayoutMapper
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public GenericLayout mapLayout(Container rContainer, GenericLayout rLayout)
	{
		if (rContainer instanceof SplitPanel)
		{
			rLayout = new MaterialSplitPanelLayout();
		}
		else if (rContainer instanceof StackPanel)
		{
			rLayout = new MaterialStackPanelLayout();
		}
		else if (rLayout instanceof MenuLayout)
		{
			rLayout = new MaterialMenuLayout();
		}
		else if (rLayout instanceof ContentLayout)
		{
			rLayout =
				new MaterialContentLayout(((ContentLayout) rLayout)
										  .getLayoutType());
		}
		else if (rLayout instanceof MainViewLayout)
		{
			return new MaterialMainViewLayout();
		}

		return rLayout;
	}
}
