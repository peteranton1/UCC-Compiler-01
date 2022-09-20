Name List
---

LL(k) Parser

    list     : '[' elements ']' ;
    elements : element (',', element)+ ;
    element  : NAME | NUMBER | NAME = NUMBER | list ;

