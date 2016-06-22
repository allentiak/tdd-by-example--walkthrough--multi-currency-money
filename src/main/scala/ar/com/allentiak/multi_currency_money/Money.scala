package ar.com.allentiak.multi_currency_money

class Money(protected val amount: Int, val currency: String){
  override def equals(other: Any) = other match {
    case o:Money => {
      (amount == o.amount) &&
      this.getClass.equals(o.getClass)
    }
  }
  def times(multiplier: Int): Money = null
}

object Money {
  def dollar(amount: Int): Money = new Dollar(amount, "USD")
  def franc(amount: Int): Money = new Franc(amount, "CHF")
}

class Dollar(amount: Int, currency: String) extends Money(amount, currency){
  override def times(multiplier: Int): Money = new Money(amount * multiplier, currency)
}

class Franc(amount: Int, currency: String) extends Money(amount, currency){
  override def times(multiplier: Int): Money = new Franc(amount * multiplier, currency)
}
