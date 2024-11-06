package com.project.backend.persistence;

import com.project.backend.domain.Order;
import com.project.backend.domain.repository.OrderRepository;
import com.project.backend.persistence.crud.PedidoJpaRepository;
import com.project.backend.persistence.entity.Pedido;
import com.project.backend.persistence.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepository implements OrderRepository {

    @Autowired
    private PedidoJpaRepository pedidoJpaRepository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public List<Order> getAll() {
        return mapper.toOrders(pedidoJpaRepository.findByEstadoTrue());
    }

    @Override
    public Optional<List<Order>> getByProvider(Integer providerId) {
        return pedidoJpaRepository.findByIdProveedorAndEstadoTrue(providerId).map(pedidos -> mapper.toOrders(pedidos));
    }

    @Override
    public Optional<Order> getOrder(Integer providerId) {
        return pedidoJpaRepository.findByIdPedidoAndEstadoTrue(providerId).map(pedido -> mapper.toOrder(pedido));
    }

    @Override
    public Order save(Order order) {
        Pedido pedido = mapper.toPedido(order);
        pedido.getProductos().forEach(producto -> producto.setPedido(pedido));
        return mapper.toOrder(pedidoJpaRepository.save(pedido));
    }

    @Override
    public Void delete(Integer orderId) {
        return null;
    }
}
