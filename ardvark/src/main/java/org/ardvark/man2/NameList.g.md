Name List
---

    list     : '[' elements ']' ;
    elements : element (',', element)+ ;
    element  : NAME | NUMBER | NAME = NUMBER | list ;

