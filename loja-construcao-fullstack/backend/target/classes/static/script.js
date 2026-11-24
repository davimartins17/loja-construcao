let token = localStorage.getItem('loja_token') || '';
let usuario = JSON.parse(localStorage.getItem('loja_usuario') || 'null');
let categoriasCache = [], fornecedoresCache = [], produtosCache = [];

async function api(url, options={}) {
  const headers = {'Content-Type':'application/json', ...(options.headers || {})};
  if (token) headers.Authorization = `Bearer ${token}`;
  const response = await fetch(url, {...options, headers});
  if (response.status === 401 || response.status === 403) throw new Error('Acesso não autorizado. Entre novamente.');
  if (!response.ok) {
    let msg = `Erro ${response.status}`;
    try { const body = await response.json(); msg = body.message || body.detail || msg; } catch {}
    throw new Error(msg);
  }
  if (response.status === 204) return null;
  const text = await response.text();
  return text ? JSON.parse(text) : null;
}
function msg(id, text, ok=false){const el=document.getElementById(id);el.textContent=text;el.className='message'+(ok?' ok':'');}
function mostrarCadastro(){document.getElementById('cadastroForm').classList.toggle('hidden');}
function sair(){token='';usuario=null;localStorage.removeItem('loja_token');localStorage.removeItem('loja_usuario');location.reload();}
function entrarNoSistema(){
  document.getElementById('loginSection').classList.add('hidden');
  document.getElementById('appSection').classList.remove('hidden');
  document.getElementById('sairBtn').classList.remove('hidden');
  document.getElementById('usuarioLogado').textContent=usuario ? `Olá, ${usuario.nome}` : '';
  carregarTudo();
}
document.getElementById('loginForm').addEventListener('submit',async e=>{
  e.preventDefault();
  try{
    const r=await api('/api/auth/login',{method:'POST',body:JSON.stringify({email:email.value,senha:senha.value})});
    token=r.token;usuario={nome:r.nome,email:r.email,papel:r.papel};
    localStorage.setItem('loja_token',token);localStorage.setItem('loja_usuario',JSON.stringify(usuario));
    entrarNoSistema();
  }catch(err){msg('loginMsg',err.message);}
});
document.getElementById('cadastroForm').addEventListener('submit',async e=>{
  e.preventDefault();
  try{
    const r=await api('/api/auth/cadastro',{method:'POST',body:JSON.stringify({nome:cadNome.value,email:cadEmail.value,senha:cadSenha.value})});
    token=r.token;usuario={nome:r.nome,email:r.email,papel:r.papel};
    localStorage.setItem('loja_token',token);localStorage.setItem('loja_usuario',JSON.stringify(usuario));entrarNoSistema();
  }catch(err){msg('loginMsg',err.message);}
});
function aba(id,btn){document.querySelectorAll('#appSection > section.panel').forEach(x=>x.classList.add('hidden'));document.getElementById(id).classList.remove('hidden');document.querySelectorAll('.tab').forEach(x=>x.classList.remove('active'));btn.classList.add('active');}
async function carregarTudo(){await Promise.all([carregarCategorias(),carregarFornecedores(),carregarProdutos()]);}
async function carregarCategorias(){
  try{categoriasCache=await api('/api/categorias');categoriasTabela.innerHTML=categoriasCache.map(c=>`<tr><td>${esc(c.nome)}</td><td>${esc(c.descricao||'')}</td><td><button onclick="editarCategoria(${c.id})">Editar</button><button class="delete" onclick="excluirCategoria(${c.id})">Excluir</button></td></tr>`).join('');produtoCategoria.innerHTML='<option value="">Selecione</option>'+categoriasCache.map(c=>`<option value="${c.id}">${esc(c.nome)}</option>`).join('');}
  catch(e){msg('categoriaMsg',e.message);}
}
async function carregarFornecedores(){
  try{fornecedoresCache=await api('/api/fornecedores');fornecedoresTabela.innerHTML=fornecedoresCache.map(f=>`<tr><td>${esc(f.nome)}</td><td>${esc(f.cnpj||'')}</td><td>${esc(f.telefone||'')}</td><td>${esc(f.email||'')}</td><td><button onclick="editarFornecedor(${f.id})">Editar</button><button class="delete" onclick="excluirFornecedor(${f.id})">Excluir</button></td></tr>`).join('');produtoFornecedor.innerHTML='<option value="">Sem fornecedor</option>'+fornecedoresCache.map(f=>`<option value="${f.id}">${esc(f.nome)}</option>`).join('');}
  catch(e){msg('fornecedorMsg',e.message);}
}
async function carregarProdutos(){
  try{produtosCache=await api('/api/produtos');produtosTabela.innerHTML=produtosCache.map(p=>`<tr><td>${esc(p.nome)}</td><td>${Number(p.preco).toLocaleString('pt-BR',{style:'currency',currency:'BRL'})}</td><td>${p.quantidadeEstoque}</td><td>${esc(p.categoria||'')}</td><td><button onclick="editarProduto(${p.id})">Editar</button><button class="delete" onclick="excluirProduto(${p.id})">Excluir</button></td></tr>`).join('');}
  catch(e){msg('produtoMsg',e.message);}
}
document.getElementById('categoriaForm').addEventListener('submit',async e=>{
 e.preventDefault();const id=categoriaId.value;const body={nome:categoriaNome.value,descricao:categoriaDescricao.value};
 try{await api('/api/categorias'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(body)});limparCategoria();msg('categoriaMsg','Categoria salva!',true);await carregarCategorias();}
 catch(err){msg('categoriaMsg',err.message);}
});
function editarCategoria(id){const c=categoriasCache.find(x=>x.id===id);categoriaId.value=c.id;categoriaNome.value=c.nome;categoriaDescricao.value=c.descricao||'';}
function limparCategoria(){categoriaForm.reset();categoriaId.value='';}
async function excluirCategoria(id){if(!confirm('Excluir esta categoria?'))return;try{await api('/api/categorias/'+id,{method:'DELETE'});await carregarCategorias();}catch(e){msg('categoriaMsg',e.message);}}
document.getElementById('fornecedorForm').addEventListener('submit',async e=>{
 e.preventDefault();const id=fornecedorId.value;const body={nome:fornecedorNome.value,cnpj:fornecedorCnpj.value,telefone:fornecedorTelefone.value,email:fornecedorEmail.value};
 try{await api('/api/fornecedores'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(body)});limparFornecedor();msg('fornecedorMsg','Fornecedor salvo!',true);await carregarFornecedores();}
 catch(err){msg('fornecedorMsg',err.message);}
});
function editarFornecedor(id){const f=fornecedoresCache.find(x=>x.id===id);fornecedorId.value=f.id;fornecedorNome.value=f.nome;fornecedorCnpj.value=f.cnpj||'';fornecedorTelefone.value=f.telefone||'';fornecedorEmail.value=f.email||'';}
function limparFornecedor(){fornecedorForm.reset();fornecedorId.value='';}
async function excluirFornecedor(id){if(!confirm('Excluir este fornecedor?'))return;try{await api('/api/fornecedores/'+id,{method:'DELETE'});await carregarFornecedores();}catch(e){msg('fornecedorMsg',e.message);}}
document.getElementById('produtoForm').addEventListener('submit',async e=>{
 e.preventDefault();const id=produtoId.value;const body={nome:produtoNome.value,descricao:produtoDescricao.value,preco:Number(produtoPreco.value),quantidadeEstoque:Number(produtoEstoque.value),categoriaId:Number(produtoCategoria.value),fornecedorId:produtoFornecedor.value?Number(produtoFornecedor.value):null};
 try{await api('/api/produtos'+(id?'/'+id:''),{method:id?'PUT':'POST',body:JSON.stringify(body)});limparProduto();msg('produtoMsg','Produto salvo!',true);await carregarProdutos();}
 catch(err){msg('produtoMsg',err.message);}
});
function editarProduto(id){const p=produtosCache.find(x=>x.id===id);produtoId.value=p.id;produtoNome.value=p.nome;produtoDescricao.value=p.descricao||'';produtoPreco.value=p.preco;produtoEstoque.value=p.quantidadeEstoque;produtoCategoria.value=p.categoriaId;produtoFornecedor.value=p.fornecedorId||'';}
function limparProduto(){produtoForm.reset();produtoId.value='';}
async function excluirProduto(id){if(!confirm('Excluir este produto?'))return;try{await api('/api/produtos/'+id,{method:'DELETE'});await carregarProdutos();}catch(e){msg('produtoMsg',e.message);}}
function esc(v){return String(v??'').replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));}
if(token&&usuario){entrarNoSistema();}
