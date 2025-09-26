interface QueryCondition {
  k: string
  v: any
  m: string
}

interface QueryOrder {
  k: string
  t: 'asc' | 'desc'
}

interface QueryPagination {
  n: number
  s: number
  c: number
}

interface QueryObject {
  w: QueryCondition[]
  o: QueryOrder[]
  p: QueryPagination | {}
}

const query = {
  new(): QueryObject {
    return {
      w: [],
      o: [],
      p: {}
    }
  },

  toF(q: QueryObject, arrKv: Record<string, any>, arrKm: Record<string, string>): QueryObject {
    for (const i in arrKv) {
      this.toW(q, i, arrKv[i], arrKm[i])
    }
    return q
  },

  toW(q: QueryObject, k: string, v: any, m: string = 'LK'): QueryObject {
    if (v === null || v === '') {
      return q
    }
    q.w.push({
      k,
      v,
      m
    })
    return q
  },

  toWNull(q: QueryObject, k: string): QueryObject {
    q.w.push({
      k,
      v: null,
      m: 'IS'
    })
    return q
  },

  toO(q: QueryObject, k: string, t: 'asc' | 'desc' = 'desc'): QueryObject {
    if (!k) {
      return q
    }
    q.o.push({
      k,
      t
    })
    return q
  },

  toP(q: QueryObject, n: number, s: number, c: number): QueryObject {
    q.p = {
      n,
      s,
      c
    }
    return q
  },

  toJsonStr(q: QueryObject): string {
    return JSON.stringify(q)
  },

  toEncode(q: QueryObject): string {
    return encodeURIComponent(JSON.stringify(q))
  }
}

export default query

