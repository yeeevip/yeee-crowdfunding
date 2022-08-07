const query = {}

query.new = () => {
  return {
    'w': [],
    'o': [],
    'p': {}
  }
}

query.toF = (q, arrKv, arrKm) => {
  for (var i in arrKv) {
    query.toW(q, i, arrKv[i], arrKm[i])
  }
  return q
}

query.toW = (q, k, v, m) => {
  if (typeof m === 'undefined') {
    m = 'LK'
  }
  if (v === null) {
    return q
  }
  if (v === '') {
    return q
  }
  q.w.push({
    'k': k,
    'v': v,
    'm': m
  })
  return q
}

query.toWNull = (q, k) => {
  q.w.push({
    'k': k,
    'v': null,
    'm': 'IS'
  })
  return q
}

query.toO = (q, k, t) => {
  if (typeof k === 'undefined' || k === '') {
    return q
  }
  if (typeof t === 'undefined') {
    t = 'desc'
  }
  q.o.push({
    'k': k,
    't': t
  })
  return q
}

query.toP = (q, n, s, c) => {
  q.p = {
    'n': n,
    's': s,
    'c': c
  }
  return q
}

query.toJsonStr = (q) => {
  return JSON.stringify(q)
}

query.toEncode = (q) => {
  return encodeURIComponent(JSON.stringify(q))
}

export default query
