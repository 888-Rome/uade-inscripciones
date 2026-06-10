import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'

const API_BASE = 'http://localhost:8080/api'

const C = {
  marino: '#11335E', azul: '#2E6DB4', verde: '#6B9E2F', terracota: '#D97757',
  grafito: '#3F4A52', fondo: '#F7F9FB', tinta: '#1C2733', borde: '#E2E6EB',
}

const ALUMNO = '1186512 · Romero, Ximena Stella' // vendría del token
const PERIODO = '1er Cuatrimestre · 2026'

// Forma que devolvería GET /periodos/vigentes:
// { id, cicloLectivo, tipoPeriodo, fechaInicio, fechaFin }
const hoy = new Date()
const iso = (d) => d.toISOString().slice(0, 10)
const dMas = (n) => { const x = new Date(hoy); x.setDate(x.getDate() + n); return iso(x) }
const MOCK_PERIODOS = [
  { id: 1, cicloLectivo: 2026, tipoPeriodo: 'INSCRIPCION', fechaInicio: dMas(-3), fechaFin: dMas(30) },
  { id: 2, cicloLectivo: 2026, tipoPeriodo: 'BAJA',        fechaInicio: dMas(-3), fechaFin: dMas(30) },
  { id: 3, cicloLectivo: 2026, tipoPeriodo: 'CURSADA',     fechaInicio: dMas(-3), fechaFin: dMas(120) },
]

const TIPO_LABEL = { INSCRIPCION: 'Inscripción', BAJA: 'Bajas', CURSADA: 'Cursada', PREVIO: 'Exámenes previos' }
const fmtFecha = (s) => { if (!s) return ''; const [y, m, d] = s.split('-'); return `${d}/${m}/${y}` }

const CSS = `
  .uade-link { background:none; border:none; color:${C.azul}; cursor:pointer; font-size:13px; padding:0; }
  .uade-link:hover { text-decoration:underline; }
  .acceso { display:flex; flex-direction:column; gap:6px; text-decoration:none; background:#fff;
    border:1px solid ${C.borde}; border-radius:14px; padding:18px 20px; transition:box-shadow .15s, transform .12s; }
  .acceso:hover { box-shadow:inset 4px 0 0 ${C.verde}, 0 1px 6px rgba(28,39,51,.08);
    transform:translateY(-1px); text-decoration:none; }
`

async function fetchPeriodos() {
  const token = localStorage.getItem('token')
  const res = await fetch(`${API_BASE}/periodos/vigentes`, { headers: token ? { Authorization: `Bearer ${token}` } : {} })
  if (!res.ok) throw new Error(String(res.status))
  return res.json()
}

export default function InicioPage() {
  const [periodos, setPeriodos] = useState([])
  const [usingMock, setUsingMock] = useState(false)
  const [loading, setLoading] = useState(true)
  const [aviso, setAviso] = useState(null)

  useEffect(() => {
    fetchPeriodos()
      .then((d) => { setPeriodos(d || []); setUsingMock(false) })
      .catch(() => { setPeriodos(MOCK_PERIODOS); setUsingMock(true) })
      .finally(() => setLoading(false))
  }, [])

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
        <h1 style={{ margin: 0, color: '#fff', fontSize: 22 }}>Inicio</h1>
        <span style={{ color: 'rgba(255,255,255,.85)', fontSize: 13 }}>{PERIODO}</span>
      </div>

      <main style={{ maxWidth: 980, margin: '0 auto', padding: '22px 22px 48px' }}>
        {aviso && (
          <div style={{ background: '#EAF1F9', border: `1px solid ${C.azul}`, color: C.azul,
            padding: '10px 14px', borderRadius: 10, marginBottom: 16, fontSize: 14 }}>{aviso}</div>
        )}

        <p style={{ marginTop: 0, fontSize: 15, color: C.grafito }}>
          Hola, Ximena. Estos son los períodos habilitados hoy. Para armar tu semana, entrá a <strong>Buscar clases</strong>.
        </p>

        {/* accesos rápidos */}
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: 14, margin: '18px 0 30px' }}>
          <Acceso to="/buscar" titulo="Buscar clases" desc="Encontrá clases y agregalas al carrito." />
          <Acceso to="/inscripciones" titulo="Mis inscripciones" desc="Tus cursadas confirmadas. Baja y cambio de clase." />
          <Acceso to="/notificaciones" titulo="Notificaciones" desc="Confirmaciones, reservas por vencer y conflictos." />
        </div>

        {/* períodos vigentes */}
        <h2 style={{ fontSize: 17, margin: '0 0 12px' }}>Períodos vigentes</h2>
        {usingMock && (
          <p style={{ color: C.grafito, fontSize: 13, marginTop: -6 }}>
            Vista con datos de ejemplo (el endpoint de períodos aún no está conectado).
          </p>
        )}

        {loading ? <p style={{ color: C.grafito }}>Cargando…</p>
          : periodos.length === 0 ? <p style={{ color: C.grafito }}>No hay períodos vigentes.</p>
          : (
            <div style={{ border: `1px solid ${C.borde}`, borderRadius: 14, overflow: 'hidden',
              boxShadow: '0 1px 3px rgba(28,39,51,.05)', background: '#fff' }}>
              {periodos.map((p, idx) => (
                <div key={p.id} style={{ display: 'flex', alignItems: 'center', gap: 16, padding: '14px 18px',
                  borderTop: idx ? `1px solid ${C.fondo}` : 'none' }}>
                  <div style={{ flex: 1 }}>
                    <div style={{ fontWeight: 700, fontSize: 15 }}>{TIPO_LABEL[p.tipoPeriodo] || p.tipoPeriodo}</div>
                    <div style={{ color: C.grafito, fontSize: 13, marginTop: 2 }}>Ciclo lectivo {p.cicloLectivo}</div>
                  </div>
                  <div style={{ textAlign: 'right', fontSize: 13, color: C.grafito }}>
                    {fmtFecha(p.fechaInicio)} → {fmtFecha(p.fechaFin)}
                  </div>
                  <span style={{ background: C.verde, color: '#fff', fontSize: 12, padding: '4px 12px',
                    borderRadius: 999, whiteSpace: 'nowrap' }}>Habilitado</span>
                </div>
              ))}
            </div>
          )}
      </main>
    </div>
  )
}

function Acceso({ to, titulo, desc }) {
  return (
    <Link to={to} className="acceso">
      <span style={{ fontWeight: 700, fontSize: 15, color: C.marino }}>{titulo}</span>
      <span style={{ fontSize: 13, color: C.grafito, lineHeight: 1.45 }}>{desc}</span>
    </Link>
  )
}