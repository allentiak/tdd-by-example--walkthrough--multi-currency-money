package ar.com.allentiak.multi_currency_money

import org.scalatest.FunSpec

class MoneyTest extends FunSpec {

  describe("Multi-Currency-enabled Money") {
    it("should correctly display its currency") {
      assert(Money.dollar(1).currency === "USD")
      assert(Money.franc(1).currency === "CHF")
    }
    it("should support multiplication by a real number") {
      val fiveDollars = Money.dollar(5)
      assert(fiveDollars.times(2) === Money.dollar(10))
      assert(fiveDollars.times(3) === Money.dollar(15))
      val fiveFrancs = Money.franc(5)
      assert(fiveFrancs.times(2) === Money.franc(10))
      assert(fiveFrancs.times(3) === Money.franc(15))
    }
    describe("should support (in)equality") {
      it("internally") {
        assert(Money.dollar(5).equals(Money.dollar(5)))
        assert(Money.franc(5).equals(Money.franc(5)))
        assert(Money.dollar(5).equals(Money.dollar(6)) === false)
        assert(Money.franc(5).equals(Money.franc(6)) === false)
      }
      it("externally") {
        assert(Money.dollar(5).equals(Money.franc(5)) === false)
      }
    }
    describe("should support addition") {
      ignore("internally") {
        assert(Money.dollar(5).plus(Money.dollar(5)).equals(Money.dollar(10)))
        assert(Money.franc(5).plus(Money.franc(5)).equals(Money.franc(10)))
      }
    }
    it("should be reducible to itself") {
      val bank = new Bank
      assert(Money.dollar(1) === bank.reduce(Money.dollar(1), "USD"))
    }
    it("should be reducible across currencies (supporting convertion)") {
      val bank = new Bank
      bank.addRate("CHF", "USD", 2)    // 1 USD == 2 CHF
      assert(Money.dollar(1) === bank.reduce(Money.franc(2), "USD"))
    }

  }

  describe("Expressions") {
    it("Sums should be composed by Money") {
      val five = Money.dollar(5)
      val result: Expression = five.plus(five)
      val sum: Sum = result.asInstanceOf[Sum]
      assert(five === sum.augend)
      assert(five === sum.addend)
    }
    it("should be reducible") {
      val sum: Expression = new Sum(Money.dollar(3), Money.dollar(4))
      val bank = new Bank
      val reduced: Money = bank.reduce(sum, "USD")
      assert(Money.dollar(7) === reduced)
    }
  }

}
