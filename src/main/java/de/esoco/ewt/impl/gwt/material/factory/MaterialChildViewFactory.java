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
package de.esoco.ewt.impl.gwt.material.factory;

import gwt.material.design.client.constants.ModalType;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;

import de.esoco.ewt.EWT;
import de.esoco.ewt.component.ChildView.ChildViewFactory;
import de.esoco.ewt.component.ChildView.IsChildViewWidget;
import de.esoco.ewt.component.View;
import de.esoco.ewt.style.ViewStyle;
import de.esoco.ewt.style.ViewStyle.Flag;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;


/********************************************************************
 * A child view factory that creates instances of {@link MaterialModal}.
 *
 * @author eso
 */
public class MaterialChildViewFactory extends ChildViewFactory
{
	//~ Methods ----------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public IsChildViewWidget createChildViewWidget(
		View	  rParent,
		ViewStyle rStyle)
	{
		if (rStyle.hasFlag(ViewStyle.Flag.MODAL))
		{
			return createViewWidget(rParent, rStyle);
		}
		else
		{
			return super.createChildViewWidget(rParent, rStyle);
		}
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public IsChildViewWidget createDialogWidget(View rParent, ViewStyle rStyle)
	{
		if (rStyle.hasFlag(ViewStyle.Flag.MODAL))
		{
			return createViewWidget(rParent, rStyle);
		}
		else
		{
			return super.createChildViewWidget(rParent, rStyle);
		}
	}

	/***************************************
	 * Creates the actual view widget.
	 *
	 * @param  rParent The parent view
	 * @param  rStyle  The view style
	 *
	 * @return The view widget
	 */
	private IsChildViewWidget createViewWidget(View rParent, ViewStyle rStyle)
	{
		GewtMaterialModal aModal	    = new GewtMaterialModal(rStyle);
		Widget			  rParentWidget = rParent.getWidget();

		if (rParentWidget instanceof HasWidgets)
		{
			((HasWidgets) rParentWidget).add(aModal);
		}
		else
		{
			throw new IllegalStateException("Unsupported parent widget type");
		}

		return aModal;
	}

	//~ Inner Classes ----------------------------------------------------------

	/********************************************************************
	 * A {@link MaterialModal} subclass that also implements the child view
	 * interface.
	 *
	 * @author eso
	 */
	public static class GewtMaterialModal extends MaterialModal
		implements IsChildViewWidget
	{
		//~ Instance fields ----------------------------------------------------

		private MaterialTitle aTitle = null;

		//~ Constructors -------------------------------------------------------

		/***************************************
		 * Creates a new instance.
		 *
		 * @param rStyle The view style
		 */
		public GewtMaterialModal(ViewStyle rStyle)
		{
			setInDuration(500);
			setOutDuration(500);

			if (rStyle.hasFlag(Flag.BOTTOM))
			{
				setType(ModalType.BOTTOM_SHEET);
			}
		}

		//~ Methods ------------------------------------------------------------

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void hide()
		{
			close();

			if (getParent() instanceof HasWidgets)
			{
				((HasWidgets) getParent()).remove(this);
			}
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public boolean isShown()
		{
			return isAttached();
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void setViewTitle(String sTitle)
		{
			if (aTitle == null)
			{
				aTitle = new MaterialTitle(sTitle);
				insert(aTitle, 0);

				aTitle.addStyleName(EWT.CSS.ewtDialogTitle());
			}
			else
			{
				aTitle.setTitle(sTitle);
			}
		}

		/***************************************
		 * {@inheritDoc}
		 */
		@Override
		public void show()
		{
			open();
		}
	}
}
