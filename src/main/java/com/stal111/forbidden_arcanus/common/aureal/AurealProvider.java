package com.stal111.forbidden_arcanus.common.aureal;

/**
 * @author stal111
 * @since 17.09.2023
 */
public interface AurealProvider {

    /**
     * @return the current amount of Aureal.
     */
    int getAureal();

    /**
     * Updates the currently available Aureal.
     *
     * @param aureal the new Aureal value
     */
    void setAureal(int aureal);

    /**
     * @return the max amount of Aureal this object can contain.
     */
    int getAurealLimit();

    /**
     * Updates the max amount of Aureal this object can contain.
     *
     * @param limit the new limit
     */
    void setAurealLimit(int limit);

}
