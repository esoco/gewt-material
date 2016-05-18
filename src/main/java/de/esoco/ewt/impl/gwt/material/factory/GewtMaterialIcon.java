package de.esoco.ewt.impl.gwt.material.factory;

import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialIcon;

/********************************************************************
 * A {@link MaterialIcon} subclass that also implements {@link HasText}.
 *
 * @author eso
 */
class GewtMaterialIcon extends MaterialIcon implements HasHTML,
															  ImageAttribute
{
	//~ Instance fields ----------------------------------------------------

	private Image rImage;

	//~ Methods ------------------------------------------------------------

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public String getHTML()
	{
		return getText();
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage()
	{
		return rImage;
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setHTML(String sHtml)
	{
		getElement().setInnerText(sHtml);
	}

	/***************************************
	 * {@inheritDoc}
	 */
	@Override
	public void setImage(Image rImage)
	{
		this.rImage = rImage;

		if (rImage instanceof Icon)
		{
			setIconType(IconType.valueOf(((Icon) rImage).getName()
										 .toUpperCase()));
		}
	}
}