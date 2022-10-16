import style
survey = "Eagle Surveys J123456"

# """A question definition"""
class Q1 (style.Question) :
  """A question definition"""
  qt = "A single choice Question"
  al = [ yes, no, maybe, dkna]
  yes = {data: "Y", text: "Yes" }
  no = "No"
  maybe = "Maybe"
  dkna = { options : [single, default], text: "Dk/Na"}

class Q1yes(style.Question) :
  """A question"""
  qn = single
  qt = "Some other question"
  al = q1.al

class Q1no(style.Question):
  """A question"""
  qn = single
  qt = "Some other question"
  al = q1.al

# -------------------------
# Script starts here
#
present(Q1)

if x == "Q1(no|maybe|dkna)" :
  compute(Q1(dkna))
if x == "Q1(no|maybe|dkna)" :
  compute(Q1(dkna))

#
# Script ends here
# -------------------------
