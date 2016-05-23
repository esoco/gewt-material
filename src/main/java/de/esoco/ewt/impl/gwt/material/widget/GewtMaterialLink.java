package de.esoco.ewt.impl.gwt.material.widget;

import de.esoco.ewt.graphics.Icon;
import de.esoco.ewt.graphics.Image;
import de.esoco.ewt.property.ImageAttribute;

import com.google.gwt.user.client.ui.HasText;

import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.ui.MaterialLink;

/********************************************************************
 * A {@link MaterialLink} subclass that also implements {@link HasText}.
 *
 * @author eso
 */
public class GewtMaterialLink extends MaterialLink
	implements HasText, ImageAttribute
{
	//~ Instance fields ----------------------------------------------------

	private Image rImage;

	//~ Methods ------------------------------------------------------------

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
	public void setImage(Image rImage)
	{
		this.rImage = rImage;

		if (rImage instanceof Icon)
		{
			setIconType(IconType.valueOf(((Icon) rImage).getName()));
		}
	}
}