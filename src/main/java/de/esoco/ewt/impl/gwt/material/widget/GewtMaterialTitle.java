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
package de.esoco.ewt.impl.gwt.material.widget;

import gwt.material.design.client.ui.MaterialTitle;

import com.google.gwt.user.client.ui.HasText;

/**
 * A {@link MaterialTitle} subclass that also implements {@link HasText}.
 *
 * @author eso
 */
public class GewtMaterialTitle extends MaterialTitle implements HasText {

	private String sTitleText;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText() {
		return sTitleText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setText(String sText) {
		sTitleText = sText;

		super.setTitle(sText);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setTitle(String sTooltip) {
		super.setTooltip(sTooltip);
	}
}
