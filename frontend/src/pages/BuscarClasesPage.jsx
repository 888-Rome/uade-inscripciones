import { useEffect, useMemo, useState } from 'react'

const API_BASE = 'http://localhost:8080/api'

const C = {
  marino: '#11335E', azul: '#2E6DB4', verde: '#6B9E2F', terracota: '#D97757',
  grafito: '#3F4A52', fondo: '#F7F9FB', tinta: '#1C2733', borde: '#E7EBF0',
}

const ALUMNO = '1186512 · Romero, Ximena Stella' // vendría del token
const PERIODO = '1er Cuatrimestre · 2026'

const DIA_LARGO = { LUN: 'Lun', MAR: 'Mar', MIE: 'Mié', JUE: 'Jue', VIE: 'Vie', SAB: 'Sáb' }
const ORDEN_DIAS = ['LUN', 'MAR', 'MIE', 'JUE', 'VIE', 'SAB']
const turnoLabel = (t) => ({ MANIANA: 'Mañana', TARDE: 'Tarde', NOCHE: 'Noche' }[t] || t)
const sedeLabel = (s) => ({ LIMA: 'Lima', RECOLETA: 'Recoleta', PINAMAR: 'Pinamar' }[s] || s)
const regimenLabel = (r) => ({ FINAL_OBLIGATORIO: 'Examen final', PROMOCION: 'Promoción' }[r] || r)

const horarioRegla = (c) => {
  const dias = [...(c.dias || [])].sort((a, b) => ORDEN_DIAS.indexOf(a) - ORDEN_DIAS.indexOf(b))
  const txt = dias.length >= 5
    ? `${DIA_LARGO[dias[0]]} a ${DIA_LARGO[dias[dias.length - 1]]}`
    : dias.map((d) => DIA_LARGO[d]).join(' · ')
  return `${txt}  ${c.horaInicio}–${c.horaFin}`
}

const MOCK_CLASES = [
  { id: 1, materiaCodigo: '3.1.051', materiaNombre: 'Álgebra', cargaHoraria: 85, regimen: 'FINAL_OBLIGATORIO',
    vacantes: 29, dias: ['LUN', 'MIE'], horaInicio: '08:00', horaFin: '12:00', sede: 'LIMA', turno: 'MANIANA',
    modalidad: 'PRESENCIAL', idioma: 'Español', ofrecimiento: 'CURRICULAR', profesores: ['Moisés E. Bueno'] },
  { id: 2, materiaCodigo: '3.1.051', materiaNombre: 'Álgebra', cargaHoraria: 85, regimen: 'FINAL_OBLIGATORIO',
    vacantes: 5, dias: ['MAR', 'JUE'], horaInicio: '19:00', horaFin: '23:00', sede: 'RECOLETA', turno: 'NOCHE',
    modalidad: 'PRESENCIAL', idioma: 'Español', ofrecimiento: 'CURRICULAR', profesores: ['Nicolás A. Rossi'] },
  { id: 3, materiaCodigo: '3.4.077', materiaNombre: 'Programación III', cargaHoraria: 68, regimen: 'FINAL_OBLIGATORIO',
    vacantes: 40, dias: ['VIE'], horaInicio: '14:00', horaFin: '18:00', sede: 'LIMA', turno: 'TARDE',
    modalidad: 'PRESENCIAL', idioma: 'Español', ofrecimiento: 'CURRICULAR', profesores: ['Nahuel Gonzáles'] },
  { id: 4, materiaCodigo: '3.4.212', materiaNombre: 'Teleinformática y Redes', cargaHoraria: 68, regimen: 'FINAL_OBLIGATORIO',
    vacantes: 3, dias: ['LUN', 'MAR', 'MIE', 'JUE', 'VIE', 'SAB'], horaInicio: '07:30', horaFin: '17:30',
    sede: 'PINAMAR', turno: 'MANIANA', modalidad: 'PRESENCIAL', idioma: 'Español', ofrecimiento: 'CURRICULAR',
    profesores: ['M. Bueno', 'N. Rossi'] },
]
const MOCK_SIN_CLASES = ['Matemática Discreta', 'Física I', 'Fundamentos de Química']

const CSS = `
  .uade-input { width:100%; padding:12px 15px; border:1px solid ${C.borde}; border-radius:11px;
    font-size:15px; outline:none; background:#fff; transition:border-color .15s, box-shadow .15s; box-sizing:border-box; }
  .uade-input:focus { border-color:${C.azul}; box-shadow:0 0 0 3px rgba(46,109,180,.12); }
  .uade-select { padding:8px 11px; border:1px solid ${C.borde}; border-radius:9px; background:#fff;
    font-size:14px; color:${C.tinta}; outline:none; cursor:pointer; }
  .uade-chip { padding:6px 13px; border-radius:999px; cursor:pointer; font-size:13px; border:1px solid ${C.borde};
    background:#fff; color:${C.tinta}; transition:all .12s; }
  .uade-chip:hover { border-color:${C.azul}; }
  .uade-chip.on { background:${C.azul}; border-color:${C.azul}; color:#fff; }
  .uade-filtrar { padding:9px 30px; border:none; border-radius:9px; background:${C.verde}; color:#fff;
    font-size:14px; font-weight:600; cursor:pointer; transition:filter .15s; }
  .uade-filtrar:hover { filter:brightness(1.06); }
  .uade-agregar { background:${C.azul}; color:#fff; border:none; padding:9px 20px; border-radius:9px;
    cursor:pointer; font-size:14px; transition:filter .15s; }
  .uade-agregar:hover { filter:brightness(1.08); }
  .uade-link { background:none; border:none; color:${C.azul}; cursor:pointer; font-size:13px; padding:0; }
  .uade-link:hover { text-decoration:underline; }
  .uade-card { transition:box-shadow .15s; }
  .uade-card:hover { box-shadow:inset 4px 0 0 ${C.verde}; }
`

async function fetchClases() {
  const token = localStorage.getItem('token')
  const res = await fetch(`${API_BASE}/clases/buscar`, { headers: token ? { Authorization: `Bearer ${token}` } : {} })
  if (!res.ok) throw new Error(String(res.status))
  return res.json()
}

export default function BuscarClasesPage() {
  const [clases, setClases] = useState([])
  const [usingMock, setUsingMock] = useState(false)
  const [loading, setLoading] = useState(true)
  const [query, setQuery] = useState('')
  const [staged, setStaged] = useState({ turno: '', ofrecimiento: 'CURRICULAR', dias: [], carrera: '1621' })
  const [applied, setApplied] = useState({ turno: '', ofrecimiento: 'CURRICULAR', dias: [] })
  const [aviso, setAviso] = useState(null)

  useEffect(() => {
    fetchClases()
      .then((d) => { setClases(d || []); setUsingMock(false) })
      .catch(() => { setClases(MOCK_CLASES); setUsingMock(true) })
      .finally(() => setLoading(false))
  }, [])

  const toggleDia = (d) => setStaged((s) => ({
    ...s, dias: s.dias.includes(d) ? s.dias.filter((x) => x !== d) : [...s.dias, d],
  }))
  const filtrar = () => setApplied({ turno: staged.turno, ofrecimiento: staged.ofrecimiento, dias: staged.dias })

  const grupos = useMemo(() => {
    const vis = clases.filter((c) => {
      const okQ = !query || c.materiaNombre.toLowerCase().includes(query.toLowerCase())
      const okT = !applied.turno || c.turno === applied.turno
      const okD = !applied.dias.length || c.dias.some((d) => applied.dias.includes(d))
      const okO = !applied.ofrecimiento || (c.ofrecimiento || 'CURRICULAR') === applied.ofrecimiento
      return okQ && okT && okD && okO
    })
    const map = new Map()
    for (const c of vis) {
      if (!map.has(c.materiaCodigo)) map.set(c.materiaCodigo, { materia: c, clases: [] })
      map.get(c.materiaCodigo).clases.push(c)
    }
    for (const g of map.values()) g.clases.sort((a, b) => b.vacantes - a.vacantes)
    return [...map.values()]
  }, [clases, query, applied])

  return (
    <div style={{ background: C.fondo, minHeight: '100vh', color: C.tinta, fontFamily: 'system-ui, sans-serif' }}>
      <style>{CSS}</style>

      {/* banner */}
      <div style={{ background: C.marino, padding: '13px 26px', display: 'flex', alignItems: 'center', gap: 14 }}>
        <span style={{ color: '#fff', fontWeight: 800, letterSpacing: '.05em', fontSize: 15 }}>INSCRIPCIONES</span>
        <span style={{ background: C.verde, color: '#fff', fontSize: 12.5, padding: '4px 12px', borderRadius: 999 }}>{ALUMNO}</span>
        <span style={{ marginLeft: 'auto', color: '#fff', fontWeight: 800, letterSpacing: '.06em', fontSize: 18 }}>UADE</span>
      </div>
      <div style={{ background: '#fff', padding: '5px 26px', textAlign: 'right', borderBottom: `1px solid ${C.borde}` }}>
        <button className="uade-link" style={{ color: C.verde }}
          onClick={() => { localStorage.removeItem('token'); setAviso('Sesión cerrada (demo).') }}>¿Cerrar Sesión?</button>
      </div>

      {/* franja verde de sección */}
      <div style={{ background: C.verde, padding: '14px 26px', display: 'flex', alignItems: 'baseline', gap: 12 }}>
        <h1 style={{ margin: 0, color: '#fff', fontSize: 22 }}>Buscar clases</h1>
        <span style={{ color: 'rgba(255,255,255,.85)', fontSize: 13 }}>{PERIODO}</span>
      </div>

      <main style={{ maxWidth: 980, margin: '0 auto', padding: '22px 22px 48px' }}>

        {/* bloque único: búsqueda + filtros + Filtrar */}
        <div style={{ background: '#fff', border: `1px solid ${C.borde}`, borderRadius: 14,
          padding: 20, boxShadow: '0 1px 3px rgba(28,39,51,.05)', marginBottom: 24 }}>

          <input className="uade-input" value={query} onChange={(e) => setQuery(e.target.value)}
            placeholder="Buscar materia por nombre…" />

          <div style={{ display: 'grid', gridTemplateColumns: '1.1fr 1fr 1fr', gap: 22, marginTop: 18 }}>
            <div style={{ display: 'grid', gap: 11 }}>
              <Campo label="Carrera">
                <select className="uade-select" value={staged.carrera}
                  onChange={(e) => setStaged({ ...staged, carrera: e.target.value })}>
                  <option value="1621">Ing. en Informática (Plan 1621)</option>
                </select>
              </Campo>
              <Campo label="Turno">
                <select className="uade-select" value={staged.turno}
                  onChange={(e) => setStaged({ ...staged, turno: e.target.value })}>
                  <option value="">Todos</option>
                  <option value="MANIANA">Mañana</option>
                  <option value="TARDE">Tarde</option>
                  <option value="NOCHE">Noche</option>
                </select>
              </Campo>
              <Campo label="Ofrecimiento">
                <div style={{ display: 'flex', gap: 14, fontSize: 14, alignItems: 'center' }}>
                  {['CURRICULAR', 'OPTATIVA'].map((o) => (
                    <label key={o} style={{ display: 'flex', gap: 5, alignItems: 'center', cursor: 'pointer' }}>
                      <input type="radio" name="ofrec" checked={staged.ofrecimiento === o}
                        onChange={() => setStaged({ ...staged, ofrecimiento: o })} />
                      {o === 'CURRICULAR' ? 'Curricular' : 'Optativa'}
                    </label>
                  ))}
                </div>
              </Campo>
            </div>

            <div>
              <div style={{ fontSize: 13, color: C.grafito, marginBottom: 6 }}>Materias</div>
              <div style={{ fontSize: 13, color: C.grafito, lineHeight: 1.5 }}>
                Buscás por nombre en el campo de arriba. Mostramos primero lo que te falta del plan.
              </div>
            </div>

            <div>
              <div style={{ fontSize: 13, color: C.grafito, marginBottom: 8 }}>¿Qué días preferís?</div>
              <div style={{ display: 'flex', flexWrap: 'wrap', gap: 7 }}>
                {ORDEN_DIAS.map((d) => (
                  <button key={d} className={`uade-chip${staged.dias.includes(d) ? ' on' : ''}`}
                    onClick={() => toggleDia(d)}>{DIA_LARGO[d]}</button>
                ))}
              </div>
            </div>
          </div>

          <div style={{ display: 'flex', justifyContent: 'center', marginTop: 20 }}>
            <button className="uade-filtrar" onClick={filtrar}>Filtrar</button>
          </div>
        </div>

        {usingMock && (
          <p style={{ color: C.grafito, fontSize: 13, marginTop: -8 }}>
            Vista con datos de ejemplo (el endpoint de búsqueda aún no está conectado).
          </p>
        )}
        {aviso && (
          <div style={{ background: '#EAF1F9', border: `1px solid ${C.azul}`, color: C.azul,
            padding: '10px 14px', borderRadius: 10, marginBottom: 16, fontSize: 14 }}>{aviso}</div>
        )}

        {loading ? <p style={{ color: C.grafito }}>Cargando…</p>
          : grupos.length === 0 ? <p style={{ color: C.grafito }}>No hay clases que coincidan con tu búsqueda.</p>
          : grupos.map((g) => (
            <section key={g.materia.materiaCodigo} style={{ marginBottom: 22, borderRadius: 14, overflow: 'hidden',
              border: `1px solid ${C.borde}`, boxShadow: '0 1px 3px rgba(28,39,51,.05)' }}>
              <div style={{ background: C.marino, padding: '11px 18px' }}>
                <span style={{ color: '#fff', fontWeight: 700, letterSpacing: '.03em' }}>
                  {g.materia.materiaNombre.toUpperCase()}
                </span>
              </div>
              <div style={{ background: '#fff', padding: '8px 18px', color: C.grafito, fontSize: 13,
                borderBottom: `1px solid ${C.borde}` }}>
                {g.materia.cargaHoraria ? `${g.materia.cargaHoraria} hs · ` : ''}{regimenLabel(g.materia.regimen)}
              </div>
              {g.clases.map((c) => (
                <ClaseRow key={c.id} clase={c}
                  onAgregar={() => setAviso(`Carrito todavía no implementado — agregarías ${c.materiaNombre} (${sedeLabel(c.sede)}).`)} />
              ))}
            </section>
          ))}

        {usingMock && (
          <details style={{ marginTop: 8, color: C.grafito }}>
            <summary style={{ cursor: 'pointer', fontSize: 14 }}>
              Materias sin clases en este período ({MOCK_SIN_CLASES.length})
            </summary>
            <ul style={{ margin: '8px 0 0', paddingLeft: 18, fontSize: 14 }}>
              {MOCK_SIN_CLASES.map((m) => <li key={m} style={{ margin: '2px 0' }}>{m}</li>)}
            </ul>
          </details>
        )}
      </main>
    </div>
  )
}

function Campo({ label, children }) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
      <span style={{ width: 9, height: 9, background: C.verde, borderRadius: 2, flexShrink: 0 }} />
      <span style={{ fontSize: 13, color: C.grafito, width: 88, flexShrink: 0 }}>{label}</span>
      {children}
    </div>
  )
}

function ClaseRow({ clase, onAgregar }) {
  const [open, setOpen] = useState(false)
  const escasa = clase.vacantes <= 5
  return (
    <div className="uade-card" style={{ background: '#fff', padding: 18, display: 'flex', gap: 20,
      alignItems: 'flex-start', borderTop: `1px solid ${C.fondo}` }}>
      <div style={{ textAlign: 'center', minWidth: 92 }}>
        <div style={{ fontSize: 30, fontWeight: 800, lineHeight: 1, color: escasa ? C.terracota : C.verde }}>
          {clase.vacantes}
        </div>
        <div style={{ fontSize: 12, color: C.grafito, marginTop: 2 }}>
          {clase.vacantes === 1 ? 'vacante' : 'vacantes'}
        </div>
        <button className="uade-agregar" style={{ marginTop: 12 }} onClick={onAgregar}>Agregar</button>
      </div>
      <div style={{ flex: 1 }}>
        <div style={{ fontSize: 17, fontWeight: 700 }}>{horarioRegla(clase)}</div>
        <div style={{ fontSize: 15, marginTop: 3 }}>{sedeLabel(clase.sede)}</div>
        <div style={{ fontSize: 14, color: C.grafito, marginTop: 3 }}>{(clase.profesores || []).join(', ')}</div>
        {open && (
          <div style={{ marginTop: 10, paddingTop: 10, borderTop: `1px solid ${C.borde}`,
            fontSize: 13, color: C.grafito, display: 'grid', gap: 3 }}>
            <span>Turno: {turnoLabel(clase.turno)}</span>
            <span>Modalidad: {clase.modalidad ? clase.modalidad.toLowerCase() : ''}</span>
            <span>Idioma: {clase.idioma}</span>
            <span>Código: {clase.materiaCodigo}</span>
          </div>
        )}
        <button className="uade-link" style={{ marginTop: 9 }} onClick={() => setOpen((v) => !v)}>
          {open ? 'Menos detalles' : 'Más detalles'}
        </button>
      </div>
    </div>
  )
}