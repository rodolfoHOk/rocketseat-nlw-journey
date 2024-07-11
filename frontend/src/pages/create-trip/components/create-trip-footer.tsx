export function CreateTripFooter() {
  return (
    <p className="text-sm text-zinc-500">
      <span>
        Ao planejar sua viagem pela plann.er você automaticamente concorda
      </span>
      <br />
      <span>{`com nossos `}</span>
      <a className="text-zinc-300 underline" href="#">
        termos de uso
      </a>
      <span>{` e `}</span>
      <a className="text-zinc-300 underline" href="#">
        políticas de privacidade
      </a>
      <span>.</span>
    </p>
  );
}
