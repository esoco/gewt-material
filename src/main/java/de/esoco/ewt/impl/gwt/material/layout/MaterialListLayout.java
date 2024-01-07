//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// This file is a part of the 'gewt-material' project.
// Copyright 2019 Elmar Sonnenschein, esoco GmbH, Flensburg, Germany
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

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import static de.esoco.lib.property.LayoutProperties.HORIZONTAL_ALIGN;
import static de.esoco.lib.property.StyleProperties.LIST_LAYOUT_STYLE;
import static de.esoco.lib.property.StyleProperties.MULTI_SELECTION;

/**
 * GWT Material implementation for list-style layouts.
 *
 * @author eso
 */
public class MaterialListLayout extends AbstractMaterialLayout
	implements SingleSelection {

	private ListLayoutStyle listStyle;

	private MaterialCollectionItem currentItem;

	private SingleSelection selectionWidget;

	@Override
	public void addWidget(HasWidgets container, Widget widget, StyleData style,
		int index) {
		if (listStyle == ListLayoutStyle.SIMPLE) {
			if (widget instanceof MaterialTitle) {
				MaterialTitle listTitle = (MaterialTitle) widget;

				((MaterialCollection) container).setHeader(
					listTitle.getTitle());
			} else if (!(widget instanceof MaterialCollectionItem)) {
				if (currentItem != null &&
					style.getProperty(HORIZONTAL_ALIGN, null) ==
						Alignment.END) {
					MaterialCollectionSecondary secondary =
						new MaterialCollectionSecondary();

					currentItem.add(secondary);
					secondary.add(widget);
					currentItem = null;
				} else {
					currentItem = new MaterialCollectionItem();
					currentItem.add(widget);
				}

				widget = currentItem;
			}
		} else if (!(widget instanceof MaterialCollapsibleItem)) {
			widget = new MaterialCollapsibleItem<Object>(widget);
		}

		if (widget != null) {
			super.addWidget(container, widget, style, index);
		}
	}

	@Override
	public int getSelectionIndex() {
		return selectionWidget != null ?
		       selectionWidget.getSelectionIndex() :
		       -1;
	}

	@Override
	public void setSelection(int index) {
		if (selectionWidget != null) {
			selectionWidget.setSelection(index);
		}
	}

	@Override
	protected MaterialWidget creatMaterialLayoutContainer(Container container,
		StyleData containerStyle) {
		listStyle = containerStyle.getProperty(LIST_LAYOUT_STYLE,
			ListLayoutStyle.SIMPLE);

		MaterialWidget containerWidget;

		if (listStyle == ListLayoutStyle.SIMPLE) {
			containerWidget = new MaterialCollection();
		} else {
			GewtMaterialCollapsible collapsible =
				new GewtMaterialCollapsible();

			collapsible.setAccordion(!containerStyle.hasFlag(MULTI_SELECTION));
			collapsible.setType(listStyle == ListLayoutStyle.POPOUT ?
			                    CollapsibleType.POPOUT :
			                    CollapsibleType.FLAT);
			containerWidget = collapsible;
			selectionWidget = collapsible;
		}

		return containerWidget;
	}

	/**
	 * A {@link MaterialCollapsible} subclass that also implements single
	 * selection.
	 *
	 * @author eso
	 */
	public static class GewtMaterialCollapsible extends MaterialCollapsible
		implements SingleSelection {

		private int selection = -1;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int getSelectionIndex() {
			int index = 0;

			selection = -1;

			for (Widget item : getChildren()) {
				if (StyleHelper.containsStyle(item.getElement().getClassName(),
					"active")) {
					selection = index;

					break;
				} else {
					index++;
				}
			}

			return selection;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setActive(int index) {
			selection = index - 1;

			super.setActive(index);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setSelection(int newSelection) {
			if (newSelection >= 0) {
				// prevent multiple setting of the same value so that
				// collapsible animation is not suppressed
				if (newSelection != selection) {
					setActive(newSelection + 1);
				}
			} else {
				clearActive();
			}

			collapsible(getElement());
		}
	}
}
