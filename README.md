# checkout

A Clojure implementation of [Kata Nine: Back to the CheckOut @ Code Kata] (http://codekata.pragprog.com/2007/01/kata_nine_back_.html).

<table>
  <tr>
    <th>Item</th><th>Unit Price</th><th>Special Price</th>
  </tr>
  <tr>
    <td>A</td><td>50</td><td>3 for 130</td>
  </tr>
  <tr>
    <td>B</td><td>30</td><td>2 for 45</td>
  </tr> 
  <tr>
    <td>C</td><td>20</td><td></td>
  </tr> 
  <tr>
    <td>D</td><td>15</td><td></td>
  </tr>
</table>

Our checkout accepts items in any order, so that if we scan a B, an A, and another B, we’ll recognize the two B’s and price them at 45 (for a total price so far of 95). Because the pricing changes frequently, we need to be able to pass in a set of pricing rules each time we start handling a checkout transaction.

## Objectives of the Kata

To some extent, this is just a fun little problem. But underneath the covers, it’s a stealth exercise in decoupling. The challenge description doesn’t mention the format of the pricing rules. How can these be specified in such a way that the checkout doesn’t know about particular items and their pricing strategies? How can we make the design flexible enough so that we can add new styles of pricing rule in the future?


## Usage

Run the unit tests

   <pre>$ lein test</pre>



## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
