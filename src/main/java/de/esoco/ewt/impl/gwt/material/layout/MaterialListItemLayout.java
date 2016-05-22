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

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialTitle;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.layout.GenericLayout;
import de.esoco.ewt.style.StyleData;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListItemLayout extends GenericLayout
{
	//~ Instance fields --------------------------------------------------------

	private MaterialCollapsibleHeader aItemHeader = null;
	private MaterialCollapsibleBody   aItemBody   = null;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyleData,
						  int		 nIndex)
	{
		if (rWidget instanceof MaterialTitle)
		{
			rWidget = new MaterialCollapsibleHeader(rWidget);
		}
		else if (!(rWidget instanceof MaterialCollapsibleHeader) &&
				 !(rWidget instanceof MaterialCollapsibleBody))
		{
			if (aItemHeader == null)
			{
				aItemHeader = new MaterialCollapsibleHeader(rWidget);
				rWidget     = aItemHeader;
			}
			else if (aItemBody == null)
			{
				aItemBody = new MaterialCollapsibleBody(rWidget);
				rWidget   = aItemBody;
			}
			else
			{
				aItemBody.add(rWidget);
				rWidget = null;
			}
		}

		if (rWidget != null)
		{
			super.addWidget(rContainer, rWidget, rStyleData, nIndex);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public HasWidgets createLayoutContainer(
		Container rContainer,
		StyleData rStyle)
	{
		HasWidgets aHasWidgets;

		if (rContainer.getParent().getWidget() instanceof MaterialCollapsible)
		{
			aHasWidgets = new MaterialCollapsibleItem();
		}
		else
		{
			aHasWidgets = new MaterialCollectionItem();
		}

		return aHasWidgets;
	}
}
