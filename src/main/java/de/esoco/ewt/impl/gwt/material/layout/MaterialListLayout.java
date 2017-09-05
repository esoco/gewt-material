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
package de.esoco.ewt.impl.gwt.material.layout;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.StyleHelper;
import gwt.material.design.client.constants.CollapsibleType;
import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialCollectionSecondary;
import gwt.material.design.client.ui.MaterialTitle;

import de.esoco.ewt.component.Container;
import de.esoco.ewt.style.StyleData;

import de.esoco.lib.property.Alignment;
import de.esoco.lib.property.ListLayoutStyle;
import de.esoco.lib.property.SingleSelection;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.HORIZONTAL_ALIGN;
import static de.esoco.lib.property.StyleProperties.LIST_LAYOUT_STYLE;
import static de.esoco.lib.property.StyleProperties.MULTI_SELECTION;


/********************************************************************
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListLayout extends AbstractMaterialLayout
	implements SingleSelection
{
	//~ Instance fields --------------------------------------------------------

	private ListLayoutStyle		   eListStyle;
	private MaterialCollectionItem aCurrentItem;
	private SingleSelection		   aSelectionWidget;

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
		if (eListStyle == ListLayoutStyle.SIMPLE)
		{
			if (rWidget instanceof MaterialTitle)
			{
				MaterialTitle rListTitle = (MaterialTitle) rWidget;

				((MaterialCollection) rContainer).setHeader(rListTitle
															.getTitle());
			}
			else if (!(rWidget instanceof MaterialCollectionItem))
			{
				if (aCurrentItem != null &&
					rStyle.getProperty(HORIZONTAL_ALIGN, null) == Alignment.END)
				{
					MaterialCollectionSecondary aSecondary =
						new MaterialCollectionSecondary();

					aCurrentItem.add(aSecondary);
					aSecondary.add(rWidget);
					aCurrentItem = null;
				}
				else
				{
					aCurrentItem = new MaterialCollectionItem();
					aCurrentItem.add(rWidget);
				}

				rWidget = aCurrentItem;
			}
		}
		else if (!(rWidget instanceof MaterialCollapsibleItem))
		{
			rWidget = new MaterialCollapsibleItem(rWidget);
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
	public int getSelectionIndex()
	{
		return aSelectionWidget != null ? aSelectionWidget.getSelectionIndex()
										: -1;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(int nIndex)
	{
		if (aSelectionWidget != null)
		{
			aSelectionWidget.setSelection(nIndex);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	protected MaterialWidget creatMaterialLayoutContainer(
		Container rContainer,
		StyleData rContainerStyle)
	{
		eListStyle =
			rContainerStyle.getProperty(LIST_LAYOUT_STYLE,
										ListLayoutStyle.SIMPLE);

		MaterialWidget aContainerWidget;

		if (eListStyle == ListLayoutStyle.SIMPLE)
		{
			aContainerWidget = new MaterialCollection();
		}
		else
		{
			GewtMaterialCollapsible aCollapsible =
				new GewtMaterialCollapsible();

			aCollapsible.setAccordion(!rContainerStyle.hasFlag(MULTI_SELECTION));
			aCollapsible.setType(eListStyle == ListLayoutStyle.POPOUT
								 ? CollapsibleType.POPOUT
								 : CollapsibleType.FLAT);
			aContainerWidget = aCollapsible;
			aSelectionWidget = aCollapsible;
		}

		return aContainerWidget;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialCollapsible} subclass that also implements single
	 * selection.
	 *
	 * @author eso
	 */
	public static class GewtMaterialCollapsible extends MaterialCollapsible
		implements SingleSelection
	{
		//~ Instance fields ----------------------------------------------------

		private int nSelection = -1;

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public int getSelectionIndex()
		{
			int nIndex = 0;

			nSelection = -1;

			for (Widget rItem : getChildren())
			{
				if (StyleHelper.containsStyle(rItem.getElement().getClassName(),
											  "active"))
				{
					nSelection = nIndex;

					break;
				}
				else
				{
					nIndex++;
				}
			}

			return nSelection;
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setActive(int nIndex)
		{
			nSelection = nIndex - 1;

			super.setActive(nIndex);
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setSelection(int nNewSelection)
		{
			if (nNewSelection >= 0)
			{
				// prevent multiple setting of the same value so that
				// collapsible animation is not suppressed
				if (nNewSelection != nSelection)
				{
					setActive(nNewSelection + 1);
				}
			}
			else
			{
				clearActive();
			}

			collapsible();
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		protected void collapsible(Element rE, boolean rAccordion)
		{
			super.collapsible(rE, rAccordion);
			JsMaterialElement.$(".no-collapse")
							 .on("click",
								 e ->
				 				{
				 					e.stopPropagation();

				 					return Boolean.TRUE;
								 });
		}
	}
}
