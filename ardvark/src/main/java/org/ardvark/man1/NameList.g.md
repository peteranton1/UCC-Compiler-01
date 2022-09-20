 Name List
---

LL(1) Parser

    list     : '[' elements ']' ;
    elements : element (',', element)+ ;
    element  : NAME | NUMBER | list ;

