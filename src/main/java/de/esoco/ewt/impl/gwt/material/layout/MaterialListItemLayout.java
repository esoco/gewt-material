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

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialCollapsibleHeader;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialTitle;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.HORIZONTAL_ALIGN;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListItemLayout extends AbstractMaterialLayout
{
	//~ Instance fields --------------------------------------------------------

	private boolean bIsCollapsible;

	private MaterialCollapsibleHeader aItemHeader = null;
	private MaterialCollapsibleBody   aItemBody   = null;

	private MaterialCollectionSecondary aSecondary = null;

	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void addWidget(HasWidgets rContainer,
						  Widget	 rWidget,
						  StyleData  rStyle,
						  int		 nIndex)
	{
		if (bIsCollapsible)
		{
			rWidget = createCollapsibleItemWidget(rWidget);
		}
		else
		{
			rWidget = createCollectionItemWidget(rWidget, rStyle);
		}

		if (rWidget != null)
		{
			super.addWidget(rContainer, rWidget, rStyle, nIndex);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void clear(HasWidgets rContainer)
	{
		super.clear(rContainer);

		aItemHeader = null;
		aItemBody   = null;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		MaterialWidget aHasWidgets;

		bIsCollapsible =
			rContainer.getParent().getWidget() instanceof MaterialCollapsible;

		if (bIsCollapsible)
		{
			aHasWidgets = new MaterialCollapsibleItem();
		}
		else
		{
			aHasWidgets = new MaterialCollectionItem();
		}

		return aHasWidgets;
	}

	/***************************************
	 * Checks whether the given widget needs to be wrapped into a specific child
	 * widget for a {@link MaterialCollapsible} container.
	 *
	 * @param  rWidget The widget to check
	 *
	 * @return Either the original widget, a new wrapper widget or NULL if the
	 *         widget has been added to an existing wrapper
	 */
	private Widget createCollapsibleItemWidget(Widget rWidget)
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

		return rWidget;
	}

	/***************************************
	 * Checks whether the given widget needs to be wrapped into a specific child
	 * widget for a {@link MaterialCollection} container.
	 *
	 * @param  rWidget The widget to check
	 * @param  rStyle
	 *
	 * @return Either the original widget, a new wrapper widget or NULL if the
	 *         widget has been added to an existing wrapper
	 */
	private Widget createCollectionItemWidget(Widget rWidget, StyleData rStyle)
	{
		if (rStyle.getProperty(HORIZONTAL_ALIGN, null) == Alignment.END)
		{
			if (aSecondary == null)
			{
				aSecondary = new MaterialCollectionSecondary();
				rWidget    = aSecondary;
			}
			else
			{
				rWidget = null;
			}

			aSecondary.add(rWidget);
		}

		return rWidget;
	}
}
