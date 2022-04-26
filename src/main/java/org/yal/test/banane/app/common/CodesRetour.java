package org.yal.test.banane.app.common;

/**
 * @author YLE 22 sept. 2020
 * 
 */
public enum CodesRetour
{
  OK(0), BAD_CONFIG(3), LOCKED(4), LOCKED_NOT_FOUND(5);
  
  private int code = 0;
  
  private CodesRetour(int code)
    {
      this.code = code;
    }
  
  public int errno()
  {
    return this.code;
  }
  
}
