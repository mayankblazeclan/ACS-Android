package com.hrfid.acs.helpers.events;

/**
 * Created by Madhur on 22/06/16.
 */
class DateItemTappedEvent {

  private final int dateClicked;

  public DateItemTappedEvent(int cmgDateClicked) {
    this.dateClicked = cmgDateClicked;
  }
}
